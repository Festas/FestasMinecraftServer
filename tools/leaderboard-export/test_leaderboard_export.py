#!/usr/bin/env python3
"""Unit tests for the pure transformation logic in leaderboard_export.py.

Run with:  python3 -m unittest discover -s tools/leaderboard-export
These tests do not touch the database – they exercise ``build_snapshot`` and the
small helpers, which is where all the ranking/merging logic lives.
"""

import os
import sys
import unittest
from unittest import mock

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import leaderboard_export as lbe  # noqa: E402


def _row(uuid, name, playtime_ms):
    return {"uuid": uuid, "name": name, "playtime_ms": playtime_ms}


class HelperTests(unittest.TestCase):
    def test_canonical_uuid_strips_dashes_and_lowercases(self):
        self.assertEqual(
            lbe.canonical_uuid("82755BA5-91EB-4CCA-A6A3-BE06D891CB3D"),
            "82755ba591eb4ccaa6a3be06d891cb3d",
        )
        self.assertEqual(lbe.canonical_uuid(None), "")
        self.assertEqual(lbe.canonical_uuid("  "), "")

    def test_dash_uuid_roundtrips_canonical(self):
        hex_key = "82755ba591eb4ccaa6a3be06d891cb3d"
        self.assertEqual(lbe.dash_uuid(hex_key), "82755ba5-91eb-4cca-a6a3-be06d891cb3d")
        self.assertEqual(lbe.dash_uuid("tooshort"), "")

    def test_coerce_ms_is_non_negative(self):
        self.assertEqual(lbe.coerce_ms(1500), 1500)
        self.assertEqual(lbe.coerce_ms(-5), 0)
        self.assertEqual(lbe.coerce_ms(None), 0)
        self.assertEqual(lbe.coerce_ms("abc"), 0)

    def test_strip_colors_removes_legacy_and_minimessage(self):
        self.assertEqual(lbe.strip_colors("&aAdmin"), "Admin")
        self.assertEqual(lbe.strip_colors("\u00a7cMod"), "Mod")
        self.assertEqual(lbe.strip_colors("<red>VIP</red>"), "VIP")
        self.assertEqual(lbe.strip_colors(None), "")

    def test_prettify_group(self):
        self.assertEqual(lbe.prettify_group("vip_plus"), "Vip Plus")
        self.assertEqual(lbe.prettify_group("owner"), "Owner")

    def test_parse_suffix_int(self):
        self.assertEqual(lbe._parse_suffix_int("weight.50", "weight."), 50)
        self.assertIsNone(lbe._parse_suffix_int("weight.high", "weight."))
        self.assertIsNone(lbe._parse_suffix_int("displayname.VIP", "weight."))

    def test_uuid_in_values_expands_both_forms(self):
        values = lbe._uuid_in_values(["82755ba591eb4ccaa6a3be06d891cb3d"])
        self.assertIn("82755ba591eb4ccaa6a3be06d891cb3d", values)
        self.assertIn("82755ba5-91eb-4cca-a6a3-be06d891cb3d", values)


class ResolveHighestGroupTests(unittest.TestCase):
    def test_picks_highest_weight(self):
        group, display, weight = lbe.resolve_highest_group(
            "a",
            user_groups={"a": {"default", "vip", "admin"}},
            primary_groups={},
            group_weights={"default": 0, "vip": 50, "admin": 100},
            group_displays={"admin": "Admin"},
            display_overrides={},
        )
        self.assertEqual(group, "admin")
        self.assertEqual(display, "Admin")
        self.assertEqual(weight, 100)

    def test_falls_back_to_primary_group(self):
        group, display, weight = lbe.resolve_highest_group(
            "a",
            user_groups={},
            primary_groups={"a": "vip"},
            group_weights={"vip": 50},
            group_displays={},
            display_overrides={},
        )
        self.assertEqual(group, "vip")
        self.assertEqual(display, "Vip")  # prettified fallback
        self.assertEqual(weight, 50)

    def test_no_groups_returns_none(self):
        self.assertEqual(
            lbe.resolve_highest_group("a", {}, {}, {}, {}, {}),
            (None, None, 0),
        )

    def test_display_override_wins_over_meta(self):
        _, display, _ = lbe.resolve_highest_group(
            "a",
            user_groups={"a": {"admin"}},
            primary_groups={},
            group_weights={"admin": 100},
            group_displays={"admin": "MetaName"},
            display_overrides={"admin": "Override"},
        )
        self.assertEqual(display, "Override")

    def test_equal_weight_tie_breaks_by_name(self):
        group, _, _ = lbe.resolve_highest_group(
            "a",
            user_groups={"a": {"zeta", "alpha"}},
            primary_groups={},
            group_weights={"zeta": 10, "alpha": 10},
            group_displays={},
            display_overrides={},
        )
        self.assertEqual(group, "alpha")


class BuildSnapshotTests(unittest.TestCase):
    def test_orders_by_playtime_and_assigns_rank(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Low", 60_000),
            _row("00000000-0000-0000-0000-000000000002", "High", 3_600_000),
            _row("00000000-0000-0000-0000-000000000003", "Mid", 600_000),
        ]
        snap = lbe.build_snapshot(rows, {}, {}, {}, {}, {}, top_n=10, now_s=1000)
        self.assertEqual(snap["generator"], "leaderboard-export")
        self.assertEqual(snap["updated"], 1000)
        self.assertEqual(snap["count"], 3)
        self.assertEqual([p["name"] for p in snap["players"]], ["High", "Mid", "Low"])
        self.assertEqual([p["rank"] for p in snap["players"]], [1, 2, 3])
        self.assertEqual(snap["players"][0]["playtimeSeconds"], 3600)

    def test_top_n_truncates(self):
        rows = [
            _row(f"00000000-0000-0000-0000-00000000000{i}", f"P{i}", i * 60_000)
            for i in range(1, 6)
        ]
        snap = lbe.build_snapshot(rows, {}, {}, {}, {}, {}, top_n=2, now_s=1000)
        self.assertEqual(snap["count"], 2)
        self.assertEqual([p["name"] for p in snap["players"]], ["P5", "P4"])

    def test_merges_rank_by_canonical_uuid(self):
        rows = [_row("82755BA5-91EB-4CCA-A6A3-BE06D891CB3D", "Festas", 120_000)]
        key = lbe.canonical_uuid(rows[0]["uuid"])
        snap = lbe.build_snapshot(
            rows,
            user_groups={key: {"admin"}},
            primary_groups={},
            group_weights={"admin": 100},
            group_displays={"admin": "Admin"},
            display_overrides={},
            top_n=10,
            now_s=1000,
        )
        player = snap["players"][0]
        self.assertEqual(player["group"], "admin")
        self.assertEqual(player["groupDisplay"], "Admin")
        self.assertEqual(player["groupWeight"], 100)

    def test_zero_and_negative_playtime_skipped(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Zero", 0),
            _row("00000000-0000-0000-0000-000000000002", "Neg", -1000),
            _row("00000000-0000-0000-0000-000000000003", "Ok", 60_000),
        ]
        snap = lbe.build_snapshot(rows, {}, {}, {}, {}, {}, top_n=10, now_s=1000)
        self.assertEqual([p["name"] for p in snap["players"]], ["Ok"])

    def test_exclude_uuids_and_names(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Keep", 120_000),
            _row("00000000-0000-0000-0000-000000000002", "ByName", 120_000),
            _row("00000000-0000-0000-0000-000000000003", "ByUuid", 120_000),
        ]
        snap = lbe.build_snapshot(
            rows, {}, {}, {}, {}, {},
            top_n=10, now_s=1000,
            exclude_uuids={lbe.canonical_uuid("00000000-0000-0000-0000-000000000003")},
            exclude_names={"byname"},
        )
        self.assertEqual([p["name"] for p in snap["players"]], ["Keep"])

    def test_min_weight_filters_low_ranks(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Admin", 120_000),
            _row("00000000-0000-0000-0000-000000000002", "Nobody", 120_000),
        ]
        key_admin = lbe.canonical_uuid(rows[0]["uuid"])
        snap = lbe.build_snapshot(
            rows,
            user_groups={key_admin: {"admin"}},
            primary_groups={},
            group_weights={"admin": 100},
            group_displays={},
            display_overrides={},
            top_n=10,
            now_s=1000,
            min_weight=50,
        )
        self.assertEqual([p["name"] for p in snap["players"]], ["Admin"])

    def test_equal_playtime_tie_breaks_by_name(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000002", "Bravo", 120_000),
            _row("00000000-0000-0000-0000-000000000001", "Alpha", 120_000),
        ]
        snap = lbe.build_snapshot(rows, {}, {}, {}, {}, {}, top_n=10, now_s=1000)
        self.assertEqual([p["name"] for p in snap["players"]], ["Alpha", "Bravo"])

    def test_missing_name_skipped(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "", 120_000),
            _row("00000000-0000-0000-0000-000000000002", "Ok", 120_000),
        ]
        snap = lbe.build_snapshot(rows, {}, {}, {}, {}, {}, top_n=10, now_s=1000)
        self.assertEqual([p["name"] for p in snap["players"]], ["Ok"])


class ResolveOptionsTests(unittest.TestCase):
    def test_defaults(self):
        opts = lbe.resolve_options({})
        self.assertEqual(opts["top_n"], lbe.DEFAULT_TOP_N)
        self.assertEqual(opts["query_limit"], lbe.DEFAULT_QUERY_LIMIT)
        self.assertIsNone(opts["min_weight"])
        self.assertEqual(opts["exclude_uuids"], set())

    def test_query_limit_never_below_top_n(self):
        opts = lbe.resolve_options({"top_n": 50, "query_limit": 10})
        self.assertEqual(opts["query_limit"], 50)

    def test_invalid_top_n_falls_back(self):
        opts = lbe.resolve_options({"top_n": "not-a-number"})
        self.assertEqual(opts["top_n"], lbe.DEFAULT_TOP_N)

    def test_display_overrides_are_normalised(self):
        opts = lbe.resolve_options({"group_display_overrides": {"Admin": "&cChef"}})
        self.assertEqual(opts["display_overrides"], {"admin": "Chef"})


class ResolveDbSettingsTests(unittest.TestCase):
    CREDS = {"PLAN_RO_DB_USER": "plan_ro", "PLAN_RO_DB_PASSWORD": "secret"}

    def test_read_timeout_defaults_and_is_independent_of_connect(self):
        with mock.patch.dict(os.environ, self.CREDS, clear=True):
            settings = lbe.resolve_db_settings({}, "PLAN_RO_DB", "s4_plan")
        self.assertEqual(settings["connect_timeout"], lbe.DEFAULT_CONNECT_TIMEOUT_S)
        self.assertEqual(settings["read_timeout"], lbe.DEFAULT_READ_TIMEOUT_S)
        self.assertGreater(settings["read_timeout"], settings["connect_timeout"])

    def test_read_timeout_from_config(self):
        with mock.patch.dict(os.environ, self.CREDS, clear=True):
            settings = lbe.resolve_db_settings({"read_timeout_seconds": 45}, "PLAN_RO_DB", "s4_plan")
        self.assertEqual(settings["read_timeout"], 45)

    def test_read_timeout_env_overrides_config(self):
        env = dict(self.CREDS, PLAN_RO_DB_READ_TIMEOUT="60")
        with mock.patch.dict(os.environ, env, clear=True):
            settings = lbe.resolve_db_settings({"read_timeout_seconds": 45}, "PLAN_RO_DB", "s4_plan")
        self.assertEqual(settings["read_timeout"], 60)

    def test_missing_credentials_raise(self):
        with mock.patch.dict(os.environ, {}, clear=True):
            with self.assertRaises(RuntimeError):
                lbe.resolve_db_settings({}, "PLAN_RO_DB", "s4_plan")


if __name__ == "__main__":
    unittest.main()

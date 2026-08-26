#!/usr/bin/env python3
"""Unit tests for the pure transformation logic in economy_export.py.

Run with:  python3 -m unittest discover -s tools/economy-export
These tests do not touch the database – they exercise ``build_server_snapshot``,
``resolve_servers`` and the small helpers, which is where all the ranking/merging
and config-validation logic lives.
"""

import os
import sys
import unittest
from unittest import mock

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import economy_export as eco  # noqa: E402


def _row(uuid, name, balance):
    return {"uuid": uuid, "name": name, "balance": balance}


def _server(server_id="survival", label="Survival", currency=""):
    return {
        "id": server_id,
        "label": label,
        "currency": currency,
        "env_prefix": eco.env_prefix_for(server_id),
        "database": {},
        "table": "CMI_users",
        "uuid_column": "player_uuid",
        "name_column": "username",
        "balance_column": "Balance",
    }


class HelperTests(unittest.TestCase):
    def test_canonical_uuid_strips_dashes_and_lowercases(self):
        self.assertEqual(
            eco.canonical_uuid("82755BA5-91EB-4CCA-A6A3-BE06D891CB3D"),
            "82755ba591eb4ccaa6a3be06d891cb3d",
        )
        self.assertEqual(eco.canonical_uuid(None), "")
        self.assertEqual(eco.canonical_uuid("  "), "")

    def test_dash_uuid_roundtrips_canonical(self):
        hex_key = "82755ba591eb4ccaa6a3be06d891cb3d"
        self.assertEqual(eco.dash_uuid(hex_key), "82755ba5-91eb-4cca-a6a3-be06d891cb3d")
        self.assertEqual(eco.dash_uuid("tooshort"), "")

    def test_coerce_balance_is_non_negative_and_finite(self):
        self.assertEqual(eco.coerce_balance(1500), 1500.0)
        self.assertEqual(eco.coerce_balance("1234.56"), 1234.56)
        self.assertEqual(eco.coerce_balance(-5), 0.0)
        self.assertEqual(eco.coerce_balance(None), 0.0)
        self.assertEqual(eco.coerce_balance("abc"), 0.0)
        self.assertEqual(eco.coerce_balance(float("inf")), 0.0)
        self.assertEqual(eco.coerce_balance(float("nan")), 0.0)

    def test_strip_colors_removes_legacy_and_minimessage(self):
        self.assertEqual(eco.strip_colors("&aAdmin"), "Admin")
        self.assertEqual(eco.strip_colors("\u00a7cMod"), "Mod")
        self.assertEqual(eco.strip_colors("<red>VIP</red>"), "VIP")
        self.assertEqual(eco.strip_colors(None), "")

    def test_prettify_group(self):
        self.assertEqual(eco.prettify_group("vip_plus"), "Vip Plus")
        self.assertEqual(eco.prettify_group("owner"), "Owner")

    def test_valid_identifier(self):
        self.assertTrue(eco.valid_identifier("CMI_users"))
        self.assertTrue(eco.valid_identifier("Balance"))
        self.assertFalse(eco.valid_identifier("CMI users"))
        self.assertFalse(eco.valid_identifier("Balance;DROP"))
        self.assertFalse(eco.valid_identifier("bal-ance"))
        self.assertFalse(eco.valid_identifier(""))
        self.assertFalse(eco.valid_identifier(None))

    def test_env_prefix_for(self):
        self.assertEqual(eco.env_prefix_for("survival"), "CMI_SURVIVAL_DB")
        self.assertEqual(eco.env_prefix_for("sky-block"), "CMI_SKYBLOCK_DB")
        self.assertEqual(eco.env_prefix_for("Mining_1"), "CMI_MINING1_DB")

    def test_parse_suffix_int(self):
        self.assertEqual(eco._parse_suffix_int("weight.50", "weight."), 50)
        self.assertIsNone(eco._parse_suffix_int("weight.high", "weight."))
        self.assertIsNone(eco._parse_suffix_int("displayname.VIP", "weight."))

    def test_uuid_in_values_expands_both_forms(self):
        values = eco._uuid_in_values(["82755ba591eb4ccaa6a3be06d891cb3d"])
        self.assertIn("82755ba591eb4ccaa6a3be06d891cb3d", values)
        self.assertIn("82755ba5-91eb-4cca-a6a3-be06d891cb3d", values)


class ResolveHighestGroupTests(unittest.TestCase):
    def test_picks_highest_weight(self):
        group, display, weight = eco.resolve_highest_group(
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
        group, display, weight = eco.resolve_highest_group(
            "a", {}, {"a": "vip"}, {"vip": 50}, {}, {},
        )
        self.assertEqual(group, "vip")
        self.assertEqual(display, "Vip")  # prettified fallback
        self.assertEqual(weight, 50)

    def test_no_groups_returns_none(self):
        self.assertEqual(eco.resolve_highest_group("a", {}, {}, {}, {}, {}), (None, None, 0))


class BuildServerSnapshotTests(unittest.TestCase):
    def test_orders_by_balance_and_assigns_rank(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Low", 100.0),
            _row("00000000-0000-0000-0000-000000000002", "High", 9999.99),
            _row("00000000-0000-0000-0000-000000000003", "Mid", 500.0),
        ]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=10)
        self.assertEqual(section["id"], "survival")
        self.assertEqual(section["label"], "Survival")
        self.assertEqual(section["count"], 3)
        self.assertEqual([p["name"] for p in section["players"]], ["High", "Mid", "Low"])
        self.assertEqual([p["rank"] for p in section["players"]], [1, 2, 3])
        self.assertEqual(section["players"][0]["balance"], 9999.99)

    def test_top_n_truncates(self):
        rows = [
            _row(f"00000000-0000-0000-0000-00000000000{i}", f"P{i}", i * 100.0)
            for i in range(1, 6)
        ]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=2)
        self.assertEqual(section["count"], 2)
        self.assertEqual([p["name"] for p in section["players"]], ["P5", "P4"])

    def test_merges_rank_by_canonical_uuid(self):
        rows = [_row("82755BA5-91EB-4CCA-A6A3-BE06D891CB3D", "Festas", 1200.0)]
        key = eco.canonical_uuid(rows[0]["uuid"])
        section = eco.build_server_snapshot(
            _server(), rows,
            user_groups={key: {"admin"}},
            primary_groups={},
            group_weights={"admin": 100},
            group_displays={"admin": "Admin"},
            display_overrides={},
            top_n=10,
        )
        player = section["players"][0]
        self.assertEqual(player["group"], "admin")
        self.assertEqual(player["groupDisplay"], "Admin")
        self.assertEqual(player["groupWeight"], 100)

    def test_zero_and_negative_balance_skipped(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Zero", 0),
            _row("00000000-0000-0000-0000-000000000002", "Neg", -1000),
            _row("00000000-0000-0000-0000-000000000003", "Ok", 60.0),
        ]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=10)
        self.assertEqual([p["name"] for p in section["players"]], ["Ok"])

    def test_min_balance_filters_low(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Rich", 5000.0),
            _row("00000000-0000-0000-0000-000000000002", "Poor", 10.0),
        ]
        section = eco.build_server_snapshot(
            _server(), rows, {}, {}, {}, {}, {}, top_n=10, min_balance=100.0
        )
        self.assertEqual([p["name"] for p in section["players"]], ["Rich"])

    def test_exclude_uuids_and_names(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "Keep", 120.0),
            _row("00000000-0000-0000-0000-000000000002", "ByName", 120.0),
            _row("00000000-0000-0000-0000-000000000003", "ByUuid", 120.0),
        ]
        section = eco.build_server_snapshot(
            _server(), rows, {}, {}, {}, {}, {},
            top_n=10,
            exclude_uuids={eco.canonical_uuid("00000000-0000-0000-0000-000000000003")},
            exclude_names={"byname"},
        )
        self.assertEqual([p["name"] for p in section["players"]], ["Keep"])

    def test_equal_balance_tie_breaks_by_name(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000002", "Bravo", 120.0),
            _row("00000000-0000-0000-0000-000000000001", "Alpha", 120.0),
        ]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=10)
        self.assertEqual([p["name"] for p in section["players"]], ["Alpha", "Bravo"])

    def test_balance_rounded_to_two_decimals(self):
        rows = [_row("00000000-0000-0000-0000-000000000001", "Cents", 1234.5678)]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=10)
        self.assertEqual(section["players"][0]["balance"], 1234.57)

    def test_missing_name_skipped(self):
        rows = [
            _row("00000000-0000-0000-0000-000000000001", "", 120.0),
            _row("00000000-0000-0000-0000-000000000002", "Ok", 120.0),
        ]
        section = eco.build_server_snapshot(_server(), rows, {}, {}, {}, {}, {}, top_n=10)
        self.assertEqual([p["name"] for p in section["players"]], ["Ok"])

    def test_currency_passed_through(self):
        section = eco.build_server_snapshot(
            _server(currency="Coins"), [], {}, {}, {}, {}, {}, top_n=10
        )
        self.assertEqual(section["currency"], "Coins")
        self.assertEqual(section["count"], 0)
        self.assertEqual(section["players"], [])


class ResolveOptionsTests(unittest.TestCase):
    def test_defaults(self):
        opts = eco.resolve_options({})
        self.assertEqual(opts["top_n"], eco.DEFAULT_TOP_N)
        self.assertEqual(opts["query_limit"], eco.DEFAULT_QUERY_LIMIT)
        self.assertIsNone(opts["min_balance"])
        self.assertEqual(opts["exclude_uuids"], set())

    def test_query_limit_never_below_top_n(self):
        opts = eco.resolve_options({"top_n": 50, "query_limit": 10})
        self.assertEqual(opts["query_limit"], 50)

    def test_invalid_top_n_falls_back(self):
        opts = eco.resolve_options({"top_n": "not-a-number"})
        self.assertEqual(opts["top_n"], eco.DEFAULT_TOP_N)

    def test_min_balance_parsed(self):
        opts = eco.resolve_options({"min_balance": "250.5"})
        self.assertEqual(opts["min_balance"], 250.5)

    def test_display_overrides_are_normalised(self):
        opts = eco.resolve_options({"group_display_overrides": {"Admin": "&cChef"}})
        self.assertEqual(opts["display_overrides"], {"admin": "Chef"})


class ResolveServersTests(unittest.TestCase):
    def _base(self):
        return {
            "servers": [
                {"id": "survival", "label": "Survival", "database": {"database": "s5_cmi_survival"}},
            ]
        }

    def test_valid_config_resolves_defaults(self):
        servers = eco.resolve_servers(self._base())
        self.assertEqual(len(servers), 1)
        srv = servers[0]
        self.assertEqual(srv["id"], "survival")
        self.assertEqual(srv["table"], eco.DEFAULT_CMI_TABLE)
        self.assertEqual(srv["balance_column"], eco.DEFAULT_CMI_BALANCE_COLUMN)
        self.assertEqual(srv["env_prefix"], "CMI_SURVIVAL_DB")

    def test_label_fallback_from_id(self):
        servers = eco.resolve_servers({"servers": [{"id": "sky_block"}]})
        self.assertEqual(servers[0]["label"], "Sky Block")

    def test_missing_servers_raises(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers({})

    def test_empty_servers_raises(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers({"servers": []})

    def test_duplicate_id_raises(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers({"servers": [{"id": "a"}, {"id": "a"}]})

    def test_missing_id_raises(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers({"servers": [{"label": "No Id"}]})

    def test_invalid_id_raises(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers({"servers": [{"id": "bad id!"}]})

    def test_sql_injection_identifier_rejected(self):
        with self.assertRaises(ValueError):
            eco.resolve_servers(
                {"servers": [{"id": "survival", "balance_column": "Balance; DROP TABLE x"}]}
            )

    def test_custom_identifiers_allowed(self):
        servers = eco.resolve_servers(
            {"servers": [{"id": "survival", "table": "cmi_user", "balance_column": "money"}]}
        )
        self.assertEqual(servers[0]["table"], "cmi_user")
        self.assertEqual(servers[0]["balance_column"], "money")


class ResolveDbSettingsTests(unittest.TestCase):
    CREDS = {"CMI_SURVIVAL_DB_USER": "cmi_ro", "CMI_SURVIVAL_DB_PASSWORD": "secret"}

    def test_defaults_and_read_timeout_independent(self):
        with mock.patch.dict(os.environ, self.CREDS, clear=True):
            settings = eco.resolve_db_settings({}, "CMI_SURVIVAL_DB", "survival")
        self.assertEqual(settings["connect_timeout"], eco.DEFAULT_CONNECT_TIMEOUT_S)
        self.assertEqual(settings["read_timeout"], eco.DEFAULT_READ_TIMEOUT_S)
        self.assertEqual(settings["database"], "survival")

    def test_config_database_used_as_default(self):
        with mock.patch.dict(os.environ, self.CREDS, clear=True):
            settings = eco.resolve_db_settings(
                {"database": "s5_cmi_survival"}, "CMI_SURVIVAL_DB", "survival"
            )
        self.assertEqual(settings["database"], "s5_cmi_survival")

    def test_env_overrides_config(self):
        env = dict(self.CREDS, CMI_SURVIVAL_DB_DATABASE="override_db", CMI_SURVIVAL_DB_READ_TIMEOUT="60")
        with mock.patch.dict(os.environ, env, clear=True):
            settings = eco.resolve_db_settings(
                {"database": "s5_cmi_survival", "read_timeout_seconds": 45},
                "CMI_SURVIVAL_DB",
                "survival",
            )
        self.assertEqual(settings["database"], "override_db")
        self.assertEqual(settings["read_timeout"], 60)

    def test_missing_credentials_raise(self):
        with mock.patch.dict(os.environ, {}, clear=True):
            with self.assertRaises(RuntimeError):
                eco.resolve_db_settings({}, "CMI_SURVIVAL_DB", "survival")


if __name__ == "__main__":
    unittest.main()

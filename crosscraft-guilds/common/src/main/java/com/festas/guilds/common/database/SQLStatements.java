package com.festas.guilds.common.database;

/**
 * SQL statement constants used by the GuildRepository.
 */
public final class SQLStatements {

    private SQLStatements() {}

    // ---------------------------------------------------------------
    // Guild CRUD
    // ---------------------------------------------------------------

    public static final String INSERT_GUILD =
            "INSERT INTO guilds (id, name, tag, description, level, xp, max_members, bank_balance, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_GUILD_BY_ID =
            "SELECT * FROM guilds WHERE id = ?";

    public static final String SELECT_GUILD_BY_NAME =
            "SELECT * FROM guilds WHERE name = ?";

    public static final String SELECT_GUILD_BY_TAG =
            "SELECT * FROM guilds WHERE tag = ?";

    public static final String UPDATE_GUILD =
            "UPDATE guilds SET name = ?, tag = ?, description = ?, level = ?, xp = ?, " +
            "max_members = ?, bank_balance = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String DELETE_GUILD =
            "DELETE FROM guilds WHERE id = ?";

    public static final String SELECT_TOP_GUILDS =
            "SELECT * FROM guilds ORDER BY xp DESC LIMIT ?";

    public static final String ADD_GUILD_XP =
            "UPDATE guilds SET xp = xp + ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String UPDATE_GUILD_LEVEL =
            "UPDATE guilds SET level = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String UPDATE_GUILD_BANK_BALANCE =
            "UPDATE guilds SET bank_balance = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    // ---------------------------------------------------------------
    // Guild Members CRUD
    // ---------------------------------------------------------------

    public static final String INSERT_MEMBER =
            "INSERT INTO guild_members (player_uuid, guild_id, rank, joined_at, contributed_xp) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_MEMBER_BY_UUID =
            "SELECT * FROM guild_members WHERE player_uuid = ?";

    public static final String SELECT_MEMBERS_BY_GUILD =
            "SELECT * FROM guild_members WHERE guild_id = ?";

    public static final String UPDATE_MEMBER_RANK =
            "UPDATE guild_members SET rank = ? WHERE player_uuid = ? AND guild_id = ?";

    public static final String UPDATE_MEMBER_CONTRIBUTED_XP =
            "UPDATE guild_members SET contributed_xp = contributed_xp + ? WHERE player_uuid = ? AND guild_id = ?";

    public static final String DELETE_MEMBER =
            "DELETE FROM guild_members WHERE player_uuid = ? AND guild_id = ?";

    public static final String DELETE_ALL_MEMBERS_BY_GUILD =
            "DELETE FROM guild_members WHERE guild_id = ?";

    // ---------------------------------------------------------------
    // Guild Invites
    // ---------------------------------------------------------------

    public static final String INSERT_INVITE =
            "INSERT INTO guild_invites (guild_id, invited_uuid, inviter_uuid, expires_at) " +
            "VALUES (?, ?, ?, ?)";

    public static final String SELECT_INVITE =
            "SELECT * FROM guild_invites WHERE guild_id = ? AND invited_uuid = ? AND expires_at > CURRENT_TIMESTAMP";

    public static final String SELECT_INVITES_FOR_PLAYER =
            "SELECT * FROM guild_invites WHERE invited_uuid = ? AND expires_at > CURRENT_TIMESTAMP";

    public static final String DELETE_INVITE =
            "DELETE FROM guild_invites WHERE guild_id = ? AND invited_uuid = ?";

    public static final String DELETE_EXPIRED_INVITES =
            "DELETE FROM guild_invites WHERE expires_at <= CURRENT_TIMESTAMP";

    // ---------------------------------------------------------------
    // Guild Bank Log
    // ---------------------------------------------------------------

    public static final String INSERT_BANK_LOG =
            "INSERT INTO guild_bank_log (guild_id, player_uuid, action, currency, amount) " +
            "VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_BANK_LOG_BY_GUILD =
            "SELECT * FROM guild_bank_log WHERE guild_id = ? ORDER BY timestamp DESC LIMIT ?";
}

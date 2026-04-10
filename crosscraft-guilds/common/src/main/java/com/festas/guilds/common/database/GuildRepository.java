package com.festas.guilds.common.database;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildMember;
import com.festas.guilds.api.GuildRank;
import com.festas.guilds.common.model.GuildBankImpl;
import com.festas.guilds.common.model.GuildImpl;
import com.festas.guilds.common.model.GuildMemberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Asynchrones CRUD-Repository für Gilden und Mitglieder.
 */
public class GuildRepository {

    private static final Logger log = LoggerFactory.getLogger(GuildRepository.class);

    private final DatabaseManager db;
    private final ExecutorService executor;

    public GuildRepository(DatabaseManager db) {
        this.db = db;
        this.executor = Executors.newFixedThreadPool(4, r -> {
            Thread t = new Thread(r, "GuildRepo-Worker");
            t.setDaemon(true);
            return t;
        });
    }

    // ---------------------------------------------------------------
    // Guild save / load
    // ---------------------------------------------------------------

    public CompletableFuture<Void> saveGuild(Guild guild) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.INSERT_GUILD +
                         " ON DUPLICATE KEY UPDATE name=VALUES(name), tag=VALUES(tag), " +
                         "description=VALUES(description), level=VALUES(level), xp=VALUES(xp), " +
                         "max_members=VALUES(max_members), bank_balance=VALUES(bank_balance), " +
                         "updated_at=CURRENT_TIMESTAMP")) {
                ps.setString(1, guild.getId());
                ps.setString(2, guild.getName());
                ps.setString(3, guild.getTag());
                ps.setString(4, guild.getDescription());
                ps.setInt(5, guild.getLevel());
                ps.setLong(6, guild.getXP());
                ps.setInt(7, guild.getMaxMembers());
                ps.setDouble(8, guild.getBank().getBalance("money"));
                ps.setTimestamp(9, Timestamp.from(guild.getCreatedAt()));
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Speichern der Gilde {}", guild.getId(), e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Optional<Guild>> findGuildById(String guildId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_GUILD_BY_ID)) {
                ps.setString(1, guildId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Guild guild = mapGuild(rs);
                        loadMembersInto(conn, guild);
                        return Optional.of(guild);
                    }
                }
            } catch (SQLException e) {
                log.error("Fehler beim Laden der Gilde {}", guildId, e);
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }, executor);
    }

    public CompletableFuture<Optional<Guild>> findGuildByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_GUILD_BY_NAME)) {
                ps.setString(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Guild guild = mapGuild(rs);
                        loadMembersInto(conn, guild);
                        return Optional.of(guild);
                    }
                }
            } catch (SQLException e) {
                log.error("Fehler beim Laden der Gilde nach Name {}", name, e);
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }, executor);
    }

    public CompletableFuture<Optional<Guild>> findGuildByPlayer(UUID playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_MEMBER_BY_UUID)) {
                ps.setString(1, playerUUID.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String guildId = rs.getString("guild_id");
                        try (PreparedStatement gs = conn.prepareStatement(SQLStatements.SELECT_GUILD_BY_ID)) {
                            gs.setString(1, guildId);
                            try (ResultSet grs = gs.executeQuery()) {
                                if (grs.next()) {
                                    Guild guild = mapGuild(grs);
                                    loadMembersInto(conn, guild);
                                    return Optional.of(guild);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                log.error("Fehler beim Laden der Gilde für Spieler {}", playerUUID, e);
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }, executor);
    }

    public CompletableFuture<List<Guild>> findTopGuilds(int limit) {
        return CompletableFuture.supplyAsync(() -> {
            List<Guild> guilds = new ArrayList<>();
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_TOP_GUILDS)) {
                ps.setInt(1, limit);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Guild guild = mapGuild(rs);
                        loadMembersInto(conn, guild);
                        guilds.add(guild);
                    }
                }
            } catch (SQLException e) {
                log.error("Fehler beim Laden der Top-Gilden", e);
                throw new RuntimeException(e);
            }
            return guilds;
        }, executor);
    }

    public CompletableFuture<Void> deleteGuild(String guildId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.DELETE_GUILD)) {
                ps.setString(1, guildId);
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Löschen der Gilde {}", guildId, e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    // ---------------------------------------------------------------
    // Member operations
    // ---------------------------------------------------------------

    public CompletableFuture<Void> saveMember(GuildMember member) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO guild_members (player_uuid, guild_id, rank, joined_at, contributed_xp) " +
                         "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE rank=VALUES(rank), " +
                         "contributed_xp=VALUES(contributed_xp)")) {
                ps.setString(1, member.getPlayerUUID().toString());
                ps.setString(2, member.getGuildId());
                ps.setString(3, member.getRank().name());
                ps.setTimestamp(4, Timestamp.from(member.getJoinedAt()));
                ps.setLong(5, member.getContributedXP());
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Speichern des Mitglieds {}", member.getPlayerUUID(), e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> deleteMember(UUID playerUUID, String guildId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.DELETE_MEMBER)) {
                ps.setString(1, playerUUID.toString());
                ps.setString(2, guildId);
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Entfernen des Mitglieds {}", playerUUID, e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> addXP(String guildId, long amount) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.ADD_GUILD_XP)) {
                ps.setLong(1, amount);
                ps.setString(2, guildId);
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Hinzufügen von XP zur Gilde {}", guildId, e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> saveInvite(String guildId, UUID invitedUUID, UUID inviterUUID, Instant expiresAt) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.INSERT_INVITE)) {
                ps.setString(1, guildId);
                ps.setString(2, invitedUUID.toString());
                ps.setString(3, inviterUUID.toString());
                ps.setTimestamp(4, Timestamp.from(expiresAt));
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Speichern der Einladung", e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Boolean> hasValidInvite(String guildId, UUID invitedUUID) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_INVITE)) {
                ps.setString(1, guildId);
                ps.setString(2, invitedUUID.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                log.error("Fehler beim Prüfen der Einladung", e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> deleteInvite(String guildId, UUID invitedUUID) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.DELETE_INVITE)) {
                ps.setString(1, guildId);
                ps.setString(2, invitedUUID.toString());
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Löschen der Einladung", e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Void> logBankTransaction(String guildId, UUID playerUUID, String action, String currency, double amount) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQLStatements.INSERT_BANK_LOG)) {
                ps.setString(1, guildId);
                ps.setString(2, playerUUID.toString());
                ps.setString(3, action);
                ps.setString(4, currency);
                ps.setDouble(5, amount);
                ps.executeUpdate();
            } catch (SQLException e) {
                log.error("Fehler beim Speichern des Bank-Logs", e);
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }

    // ---------------------------------------------------------------
    // Mapping helpers
    // ---------------------------------------------------------------

    private Guild mapGuild(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String tag = rs.getString("tag");
        String description = rs.getString("description");
        int level = rs.getInt("level");
        long xp = rs.getLong("xp");
        int maxMembers = rs.getInt("max_members");
        double bankBalance = rs.getDouble("bank_balance");
        Instant createdAt = rs.getTimestamp("created_at").toInstant();

        GuildBankImpl bank = new GuildBankImpl(id);
        bank.deposit("money", bankBalance);

        return new GuildImpl(id, name, tag, description, level, xp, maxMembers, bank, createdAt);
    }

    private void loadMembersInto(Connection conn, Guild guild) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQLStatements.SELECT_MEMBERS_BY_GUILD)) {
            ps.setString(1, guild.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("player_uuid"));
                    GuildRank rank = GuildRank.valueOf(rs.getString("rank"));
                    Instant joinedAt = rs.getTimestamp("joined_at").toInstant();
                    long contributedXP = rs.getLong("contributed_xp");
                    GuildMemberImpl member = new GuildMemberImpl(uuid, guild.getId(), rank, joinedAt, contributedXP);
                    guild.addMember(member);
                }
            }
        }
    }
}

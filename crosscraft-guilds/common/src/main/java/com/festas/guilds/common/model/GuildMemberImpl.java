package com.festas.guilds.common.model;

import com.festas.guilds.api.GuildMember;
import com.festas.guilds.api.GuildRank;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementierung des GuildMember-Interfaces.
 */
public class GuildMemberImpl implements GuildMember {

    private final UUID playerUUID;
    private final String guildId;
    private GuildRank rank;
    private final Instant joinedAt;
    private long contributedXP;

    public GuildMemberImpl(UUID playerUUID, String guildId, GuildRank rank,
                           Instant joinedAt, long contributedXP) {
        this.playerUUID = playerUUID;
        this.guildId = guildId;
        this.rank = rank;
        this.joinedAt = joinedAt;
        this.contributedXP = contributedXP;
    }

    public GuildMemberImpl(UUID playerUUID, String guildId, GuildRank rank) {
        this(playerUUID, guildId, rank, Instant.now(), 0L);
    }

    @Override public UUID getPlayerUUID() { return playerUUID; }
    @Override public String getGuildId() { return guildId; }
    @Override public GuildRank getRank() { return rank; }
    @Override public void setRank(GuildRank rank) { this.rank = rank; }
    @Override public Instant getJoinedAt() { return joinedAt; }
    @Override public long getContributedXP() { return contributedXP; }

    @Override
    public void addContributedXP(long amount) {
        this.contributedXP += amount;
    }

    @Override
    public String toString() {
        return "GuildMember{uuid=" + playerUUID + ", guild=" + guildId + ", rank=" + rank + "}";
    }
}

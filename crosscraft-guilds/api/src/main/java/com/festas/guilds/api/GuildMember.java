package com.festas.guilds.api;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a member of a guild.
 */
public interface GuildMember {

    /**
     * Returns the UUID of this member.
     */
    UUID getPlayerUUID();

    /**
     * Returns the guild ID this member belongs to.
     */
    String getGuildId();

    /**
     * Returns the rank of this member.
     */
    GuildRank getRank();

    /**
     * Sets the rank of this member.
     */
    void setRank(GuildRank rank);

    /**
     * Returns when this member joined.
     */
    Instant getJoinedAt();

    /**
     * Returns the amount of XP this member has contributed.
     */
    long getContributedXP();

    /**
     * Adds XP contribution.
     */
    void addContributedXP(long amount);

    /**
     * Checks if this member has at least the given rank.
     */
    default boolean hasRank(GuildRank rank) {
        return getRank().isAtLeast(rank);
    }

    /**
     * Returns true if this member is the guild leader.
     */
    default boolean isLeader() {
        return getRank() == GuildRank.LEADER;
    }
}

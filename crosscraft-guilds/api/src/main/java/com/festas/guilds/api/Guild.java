package com.festas.guilds.api;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a guild with all associated data.
 */
public interface Guild {

    /**
     * Returns the unique ID of this guild.
     */
    String getId();

    /**
     * Returns the display name of this guild.
     */
    String getName();

    /**
     * Sets the display name of this guild.
     */
    void setName(String name);

    /**
     * Returns the tag (short abbreviation) of this guild.
     */
    String getTag();

    /**
     * Sets the guild tag.
     */
    void setTag(String tag);

    /**
     * Returns the guild description.
     */
    String getDescription();

    /**
     * Sets the guild description.
     */
    void setDescription(String description);

    /**
     * Returns the current level of this guild.
     */
    int getLevel();

    /**
     * Sets the guild level.
     */
    void setLevel(int level);

    /**
     * Returns the total XP this guild has accumulated.
     */
    long getXP();

    /**
     * Adds XP to this guild.
     */
    void addXP(long amount);

    /**
     * Returns the maximum number of members allowed.
     */
    int getMaxMembers();

    /**
     * Sets the maximum number of members.
     */
    void setMaxMembers(int maxMembers);

    /**
     * Returns all members of this guild.
     */
    Collection<GuildMember> getMembers();

    /**
     * Returns the member with the given UUID, if present.
     */
    Optional<GuildMember> getMember(UUID playerUUID);

    /**
     * Returns the leader of this guild.
     */
    Optional<GuildMember> getLeader();

    /**
     * Adds a member to this guild with the RECRUIT rank.
     */
    void addMember(GuildMember member);

    /**
     * Removes a member from this guild.
     */
    void removeMember(UUID playerUUID);

    /**
     * Returns the number of current members.
     */
    default int getMemberCount() {
        return getMembers().size();
    }

    /**
     * Returns true if this guild is at max capacity.
     */
    default boolean isFull() {
        return getMemberCount() >= getMaxMembers();
    }

    /**
     * Returns true if the given player is a member.
     */
    default boolean hasMember(UUID playerUUID) {
        return getMember(playerUUID).isPresent();
    }

    /**
     * Returns the bank of this guild.
     */
    GuildBank getBank();

    /**
     * Returns when this guild was created.
     */
    Instant getCreatedAt();
}

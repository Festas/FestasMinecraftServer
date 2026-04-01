package com.festas.guilds.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Public API for the CrossCraft Guilds system.
 * Use {@link #getInstance()} to obtain the singleton instance.
 */
public interface GuildAPI {

    // ---------------------------------------------------------------
    // Singleton / Service Locator
    // ---------------------------------------------------------------

    /** Der gehaltene Singleton-Instance. */
    final class Holder {
        private static volatile GuildAPI instance;
    }

    /**
     * Returns the registered GuildAPI instance.
     *
     * @throws IllegalStateException if no instance has been registered yet
     */
    static GuildAPI getInstance() {
        GuildAPI inst = Holder.instance;
        if (inst == null) {
            throw new IllegalStateException("GuildAPI is not yet initialised. " +
                    "Ensure CrossCraftGuilds is loaded before accessing the API.");
        }
        return inst;
    }

    /**
     * Registers the API implementation. Called internally by the plugin.
     */
    static void register(GuildAPI api) {
        if (Holder.instance != null) {
            throw new IllegalStateException("GuildAPI is already registered.");
        }
        Holder.instance = api;
    }

    /**
     * Unregisters the API implementation. Called internally on plugin disable.
     */
    static void unregister() {
        Holder.instance = null;
    }

    // ---------------------------------------------------------------
    // Guild lifecycle
    // ---------------------------------------------------------------

    /**
     * Creates a new guild.
     *
     * @param leaderUUID UUID of the player who becomes leader
     * @param name       display name (unique)
     * @param tag        short tag (unique)
     * @return the created Guild, or empty if creation failed (name/tag taken, player already in guild)
     */
    CompletableFuture<Optional<Guild>> createGuild(UUID leaderUUID, String name, String tag);

    /**
     * Disbands a guild, removing all members.
     *
     * @param guildId ID of the guild to disband
     * @return true if disbanded successfully
     */
    CompletableFuture<Boolean> disbandGuild(String guildId);

    // ---------------------------------------------------------------
    // Guild lookup
    // ---------------------------------------------------------------

    /**
     * Returns a guild by its ID.
     */
    CompletableFuture<Optional<Guild>> getGuild(String guildId);

    /**
     * Returns a guild by its name.
     */
    CompletableFuture<Optional<Guild>> getGuildByName(String name);

    /**
     * Returns the guild a player currently belongs to.
     */
    CompletableFuture<Optional<Guild>> getPlayerGuild(UUID playerUUID);

    /**
     * Returns the top guilds ordered by XP descending.
     *
     * @param limit maximum number of results
     */
    CompletableFuture<List<Guild>> getTopGuilds(int limit);

    // ---------------------------------------------------------------
    // Membership management
    // ---------------------------------------------------------------

    /**
     * Invites a player to a guild. The invite is stored temporarily.
     *
     * @param guildId     the guild doing the inviting
     * @param inviterUUID the officer/leader sending the invite
     * @param inviteeUUID the player to invite
     * @return true if the invite was created successfully
     */
    CompletableFuture<Boolean> invitePlayer(String guildId, UUID inviterUUID, UUID inviteeUUID);

    /**
     * Adds a player as a full member (e.g. after accepting invite).
     */
    CompletableFuture<Boolean> addMember(String guildId, UUID playerUUID);

    /**
     * Removes a member from a guild.
     */
    CompletableFuture<Boolean> removeMember(String guildId, UUID playerUUID);

    /**
     * Promotes or demotes a member to the given rank.
     */
    CompletableFuture<Boolean> setMemberRank(String guildId, UUID playerUUID, GuildRank rank);

    // ---------------------------------------------------------------
    // XP / Level
    // ---------------------------------------------------------------

    /**
     * Awards XP to a guild.
     *
     * @param guildId      the guild to award XP to
     * @param sourceUUID   the player who earned the XP (for contribution tracking)
     * @param amount       amount of XP to award
     * @return true if XP was awarded (and possibly level-up triggered)
     */
    CompletableFuture<Boolean> awardXP(String guildId, UUID sourceUUID, long amount);
}

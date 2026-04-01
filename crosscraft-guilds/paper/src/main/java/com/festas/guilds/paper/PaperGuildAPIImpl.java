package com.festas.guilds.paper;

import com.festas.guilds.api.*;
import com.festas.guilds.common.config.GuildConfig;
import com.festas.guilds.common.database.GuildRepository;
import com.festas.guilds.common.model.GuildBankImpl;
import com.festas.guilds.common.model.GuildImpl;
import com.festas.guilds.common.model.GuildLevelManager;
import com.festas.guilds.common.model.GuildMemberImpl;
import com.festas.guilds.common.redis.RedisMessageBroker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Paper-spezifische Implementierung des GuildAPI-Interface.
 */
public class PaperGuildAPIImpl implements GuildAPI {

    private final GuildRepository repository;
    private final GuildLevelManager levelManager;
    private final GuildConfig config;
    private final RedisMessageBroker broker;

    public PaperGuildAPIImpl(GuildRepository repository, GuildLevelManager levelManager,
                              GuildConfig config, RedisMessageBroker broker) {
        this.repository = repository;
        this.levelManager = levelManager;
        this.config = config;
        this.broker = broker;
    }

    @Override
    public CompletableFuture<Optional<Guild>> createGuild(UUID leaderUUID, String name, String tag) {
        return getPlayerGuild(leaderUUID).thenCompose(existing -> {
            if (existing.isPresent()) {
                return CompletableFuture.completedFuture(Optional.empty());
            }
            String id = UUID.randomUUID().toString();
            GuildBankImpl bank = new GuildBankImpl(id);
            GuildImpl guild = new GuildImpl(id, name, tag, "", 1, 0,
                    config.getMaxMembersBase(), bank, Instant.now());

            GuildMemberImpl leader = new GuildMemberImpl(leaderUUID, id, GuildRank.LEADER);
            guild.addMember(leader);

            return repository.saveGuild(guild)
                    .thenCompose(v -> repository.saveMember(leader))
                    .thenApply(v -> {
                        publishIfAvailable(RedisMessageBroker.GuildEvent.GUILD_CREATED,
                                id + ":" + name + ":" + tag);
                        return Optional.of((Guild) guild);
                    });
        });
    }

    @Override
    public CompletableFuture<Boolean> disbandGuild(String guildId) {
        return repository.findGuildById(guildId).thenCompose(opt -> {
            if (opt.isEmpty()) return CompletableFuture.completedFuture(false);
            Guild guild = opt.get();
            return repository.deleteGuild(guildId).thenApply(v -> {
                publishIfAvailable(RedisMessageBroker.GuildEvent.GUILD_DISBANDED,
                        guildId + ":" + guild.getName());
                return true;
            });
        });
    }

    @Override
    public CompletableFuture<Optional<Guild>> getGuild(String guildId) {
        return repository.findGuildById(guildId);
    }

    @Override
    public CompletableFuture<Optional<Guild>> getGuildByName(String name) {
        return repository.findGuildByName(name);
    }

    @Override
    public CompletableFuture<Optional<Guild>> getPlayerGuild(UUID playerUUID) {
        return repository.findGuildByPlayer(playerUUID);
    }

    @Override
    public CompletableFuture<List<Guild>> getTopGuilds(int limit) {
        return repository.findTopGuilds(limit);
    }

    @Override
    public CompletableFuture<Boolean> invitePlayer(String guildId, UUID inviterUUID, UUID inviteeUUID) {
        return getPlayerGuild(inviteeUUID).thenCompose(existing -> {
            if (existing.isPresent()) return CompletableFuture.completedFuture(false);
            Instant expires = Instant.now().plus(config.getInviteExpireMinutes(), ChronoUnit.MINUTES);
            return repository.saveInvite(guildId, inviteeUUID, inviterUUID, expires)
                    .thenApply(v -> true);
        });
    }

    @Override
    public CompletableFuture<Boolean> addMember(String guildId, UUID playerUUID) {
        return repository.findGuildById(guildId).thenCompose(opt -> {
            if (opt.isEmpty()) return CompletableFuture.completedFuture(false);
            Guild guild = opt.get();
            if (guild.isFull()) return CompletableFuture.completedFuture(false);

            GuildMemberImpl member = new GuildMemberImpl(playerUUID, guildId, GuildRank.RECRUIT);
            guild.addMember(member);
            return repository.saveMember(member)
                    .thenCompose(v -> repository.deleteInvite(guildId, playerUUID))
                    .thenApply(v -> {
                        publishIfAvailable(RedisMessageBroker.GuildEvent.MEMBER_JOINED,
                                guildId + ":" + playerUUID);
                        return true;
                    });
        });
    }

    @Override
    public CompletableFuture<Boolean> removeMember(String guildId, UUID playerUUID) {
        return repository.deleteMember(playerUUID, guildId).thenApply(v -> {
            publishIfAvailable(RedisMessageBroker.GuildEvent.MEMBER_LEFT,
                    guildId + ":" + playerUUID);
            return true;
        });
    }

    @Override
    public CompletableFuture<Boolean> setMemberRank(String guildId, UUID playerUUID, GuildRank rank) {
        return repository.findGuildById(guildId).thenCompose(opt -> {
            if (opt.isEmpty()) return CompletableFuture.completedFuture(false);
            Guild guild = opt.get();
            return guild.getMember(playerUUID).map(member -> {
                member.setRank(rank);
                return repository.saveMember(member).thenApply(v -> {
                    publishIfAvailable(RedisMessageBroker.GuildEvent.MEMBER_RANK_CHANGED,
                            guildId + ":" + playerUUID + ":" + rank.name());
                    return true;
                });
            }).orElse(CompletableFuture.completedFuture(false));
        });
    }

    @Override
    public CompletableFuture<Boolean> awardXP(String guildId, UUID sourceUUID, long amount) {
        return repository.findGuildById(guildId).thenCompose(opt -> {
            if (opt.isEmpty()) return CompletableFuture.completedFuture(false);
            Guild guild = opt.get();
            boolean leveledUp = levelManager.processXP(guild, amount);

            // Mitglieds-Contribution aktualisieren
            guild.getMember(sourceUUID).ifPresent(m -> m.addContributedXP(amount));

            CompletableFuture<Void> saveXP = repository.addXP(guildId, amount);
            CompletableFuture<Void> saveMember = guild.getMember(sourceUUID)
                    .map(repository::saveMember)
                    .orElse(CompletableFuture.completedFuture(null));

            publishIfAvailable(RedisMessageBroker.GuildEvent.GUILD_XP_GAINED,
                    guildId + ":" + amount);

            if (leveledUp) {
                publishIfAvailable(RedisMessageBroker.GuildEvent.GUILD_LEVEL_UP,
                        guildId + ":" + guild.getLevel());
                return repository.saveGuild(guild).thenApply(v -> true);
            }

            return CompletableFuture.allOf(saveXP, saveMember).thenApply(v -> true);
        });
    }

    private void publishIfAvailable(RedisMessageBroker.GuildEvent event, String payload) {
        if (broker != null) {
            broker.publish(event, payload);
        }
    }
}

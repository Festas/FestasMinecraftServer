package com.festas.guilds.common.model;

import com.festas.guilds.api.Guild;
import com.festas.guilds.api.GuildBank;
import com.festas.guilds.api.GuildMember;
import com.festas.guilds.api.GuildRank;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementierung des Guild-Interfaces.
 */
public class GuildImpl implements Guild {

    private final String id;
    private String name;
    private String tag;
    private String description;
    private int level;
    private long xp;
    private int maxMembers;
    private final GuildBank bank;
    private final Instant createdAt;

    private final Map<UUID, GuildMember> members = new ConcurrentHashMap<>();

    public GuildImpl(String id, String name, String tag, String description,
                     int level, long xp, int maxMembers, GuildBank bank, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.level = level;
        this.xp = xp;
        this.maxMembers = maxMembers;
        this.bank = bank;
        this.createdAt = createdAt;
    }

    @Override public String getId() { return id; }

    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }

    @Override public String getTag() { return tag; }
    @Override public void setTag(String tag) { this.tag = tag; }

    @Override public String getDescription() { return description; }
    @Override public void setDescription(String description) { this.description = description; }

    @Override public int getLevel() { return level; }
    @Override public void setLevel(int level) { this.level = level; }

    @Override public long getXP() { return xp; }

    @Override
    public void addXP(long amount) {
        this.xp += amount;
    }

    @Override public int getMaxMembers() { return maxMembers; }
    @Override public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    @Override
    public Collection<GuildMember> getMembers() {
        return Collections.unmodifiableCollection(members.values());
    }

    @Override
    public Optional<GuildMember> getMember(UUID playerUUID) {
        return Optional.ofNullable(members.get(playerUUID));
    }

    @Override
    public Optional<GuildMember> getLeader() {
        return members.values().stream()
                .filter(m -> m.getRank() == GuildRank.LEADER)
                .findFirst();
    }

    @Override
    public void addMember(GuildMember member) {
        members.put(member.getPlayerUUID(), member);
    }

    @Override
    public void removeMember(UUID playerUUID) {
        members.remove(playerUUID);
    }

    @Override public GuildBank getBank() { return bank; }

    @Override public Instant getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Guild{id='" + id + "', name='" + name + "', tag='" + tag + "', level=" + level + "}";
    }
}

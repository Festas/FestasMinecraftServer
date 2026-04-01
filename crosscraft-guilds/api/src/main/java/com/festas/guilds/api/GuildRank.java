package com.festas.guilds.api;

/**
 * Represents the rank of a guild member.
 */
public enum GuildRank {

    LEADER("Anführer", 4),
    OFFICER("Offizier", 3),
    MEMBER("Mitglied", 2),
    RECRUIT("Rekrut", 1);

    private final String displayName;
    private final int level;

    GuildRank(String displayName, int level) {
        this.displayName = displayName;
        this.level = level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    /** Prüft ob dieser Rang mindestens so hoch ist wie der angegebene Rang. */
    public boolean isAtLeast(GuildRank rank) {
        return this.level >= rank.level;
    }

    public boolean canInvite() {
        return isAtLeast(OFFICER);
    }

    public boolean canKick() {
        return isAtLeast(OFFICER);
    }

    public boolean canPromote() {
        return isAtLeast(OFFICER);
    }

    public boolean canManageBank() {
        return isAtLeast(OFFICER);
    }

    public boolean canDisband() {
        return this == LEADER;
    }

    public boolean canEditDescription() {
        return isAtLeast(OFFICER);
    }
}

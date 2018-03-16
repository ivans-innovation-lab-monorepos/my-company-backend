package com.idugalic.common.team.model;

public enum TeamStatus {

    ACTIVE("Active", "active"), PASSIVE("Passive", "passive");

    private String displayName;
    private String urlSlug;

    TeamStatus(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.urlSlug = urlSlug;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrlSlug() {
        return urlSlug;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}

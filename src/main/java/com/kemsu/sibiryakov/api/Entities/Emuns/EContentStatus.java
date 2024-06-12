package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum EContentStatus {
    ACTIVE("active"),
    SELFDELETE("selfdelete"),
    BANNED("banned");

    private final String state;

    EContentStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum UserStatus {
    ACTIVE("active"),
    WARING("waring"),
    BANNED("banned"),
    DELETED("deleted");

    private final String state;

    UserStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

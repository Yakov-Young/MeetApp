package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum EMeetStatus {
    WAIT("wait"),
    AGREEMENT("agreement"),
    CANCELED("canceled"),
    BANNED("banned");

    private final String state;

    EMeetStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

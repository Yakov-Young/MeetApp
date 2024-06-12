package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum TypeStatus {
    VIEW("view"),
    VISIT("visit");

    private final String state;

    TypeStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

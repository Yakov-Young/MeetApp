package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    UNDEFINED("undefined");

    private final String state;

    Gender(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

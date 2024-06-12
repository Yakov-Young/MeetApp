package com.kemsu.sibiryakov.api.Entities.Emuns;

public enum ERole {
    USER("user"),
    MODERATOR("moderator"),
    ADMINISTRATOR("administrator");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}

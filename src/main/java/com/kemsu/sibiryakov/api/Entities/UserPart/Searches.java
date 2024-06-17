package com.kemsu.sibiryakov.api.Entities.UserPart;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "searches")
public class Searches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "search", nullable = false)
    private String search;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Searches(String search, User user, LocalDateTime createdAt) {
        this.search = search;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Searches(String search, User user) {
        this.search = search;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search.toLowerCase();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Searches{" +
                "id=" + id +
                ", search='" + search + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.kemsu.sibiryakov.api.Entities.MeetPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "meet_id", nullable = false)
    @JsonIgnore
    private Meet meet;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ContentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Meet getMeet() {
        return meet;
    }

    public Meet getMeetId() {
        return meet;
    }

    public String getStatus() {
        return status.getStatus();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

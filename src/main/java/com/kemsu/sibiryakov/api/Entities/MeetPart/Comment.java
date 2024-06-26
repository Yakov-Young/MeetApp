package com.kemsu.sibiryakov.api.Entities.MeetPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "grade", nullable = false)
    private Short grade;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "surname", "patronymic", "avatar"})
    private User user;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ContentStatus status;

    @ManyToOne
    @JoinColumn(name = "meet_id", nullable = false)
    @JsonIgnore
    private Meet meet;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Comment(String content, Short grade) {
        this.content = content;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Short getGrade() {
        return grade;
    }

    public User getUser() {
        return user;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public Meet getMeet() {
        return meet;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

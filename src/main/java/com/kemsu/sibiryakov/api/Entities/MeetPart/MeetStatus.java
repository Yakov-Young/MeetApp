package com.kemsu.sibiryakov.api.Entities.MeetPart;

import com.kemsu.sibiryakov.api.Entities.Emuns.EMeetStatus;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "meet_statuses")
public class MeetStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private EMeetStatus status;

    public Long getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status.getState();
    }
}

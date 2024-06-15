package com.kemsu.sibiryakov.api.Entities.MeetPart;

import com.kemsu.sibiryakov.api.Entities.Emuns.EMeetStatus;
import com.kemsu.sibiryakov.api.Entities.Emuns.UserStatus;
import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
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
    private Administration user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private EMeetStatus status;

    public MeetStatus setWait() {
        this.status = EMeetStatus.WAIT;
        this.createdAt = LocalDateTime.now();
        return this;
    }
    public MeetStatus setAgreement() {
        this.status = EMeetStatus.AGREEMENT;
        this.createdAt = LocalDateTime.now();
        return this;
    }

    public MeetStatus setBanned() {
        this.status = EMeetStatus.BANNED;
        this.createdAt = LocalDateTime.now();
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public Administration getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status.getState();
    }
}

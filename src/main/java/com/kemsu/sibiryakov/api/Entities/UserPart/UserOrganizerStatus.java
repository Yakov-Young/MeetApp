package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.Emuns.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "user_organizer_statuses")
public class UserOrganizerStatus {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Column(name = "note")
    private String note;
    @Getter
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties(value = {"id", "name", "surname", "patronymic", "avatar"})
    private User user;

    @Getter
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Getter
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    public UserOrganizerStatus(String note, User user, UserStatus status) {
        this.note = note;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.status = status;
    }

    public UserOrganizerStatus setDefault() {
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        return this;
    }
    public UserOrganizerStatus setWaring() {
        this.status = UserStatus.WARING;
        this.createdAt = LocalDateTime.now();
        return this;
    }
    public UserOrganizerStatus setBanned() {
        this.status = UserStatus.BANNED;
        this.createdAt = LocalDateTime.now();
        return this;
    }
    @Override
    public String toString() {
        return "UserOrganizerStatuses{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}

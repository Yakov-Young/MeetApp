package com.kemsu.sibiryakov.api.Entities;

import com.kemsu.sibiryakov.api.Entities.Emuns.TypeStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "meet_user")
public class MeetUser {
    @Override
    public String toString() {
        return "MeetUser{" +
                "id=" + id +
                ", meet=" + meet +
                ", user=" + user +
                ", typeAction=" + typeAction +
                ", createdAt=" + createdAt +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "meet_id", nullable = false)
    private Meet meet;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_action", nullable = false)
    private TypeStatus typeAction;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}

package com.kemsu.sibiryakov.api.Entities;

import com.kemsu.sibiryakov.api.Entities.Emuns.TypeStatus;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "meet_user")
public class MeetUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meet_id", nullable = false)
    private Long meetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_action", nullable = false)
    private TypeStatus typeAction;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}

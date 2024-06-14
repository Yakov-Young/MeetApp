package com.kemsu.sibiryakov.api.Entities.MeetPart;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meets")
public class Meet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name"})
    private Organizer owner;

    @Column(name = "date_start", nullable = false)
    private LocalDateTime dateStart;

    @Column(name = "date_end", nullable = false)
    private LocalDateTime dateEnd;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "cost", nullable = false)
    private Short cost;

    @Column(name = "count", nullable = false)
    private Short count;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private MeetStatus status;

    @OneToMany(mappedBy = "meet")
    private List<Comment> comments;

    @OneToMany(mappedBy = "meet")
    private List<Question> questions;

    @OneToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "meet_category",
            joinColumns = @JoinColumn(name = "meet_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIncludeProperties(value = {"id", "name"})
    private Set<Category> categories;

    public Meet(String title, String description,
                String additionalInfo) {
        this.title = title;
        this.description = description;
        this.additionalInfo = additionalInfo;
    }

}

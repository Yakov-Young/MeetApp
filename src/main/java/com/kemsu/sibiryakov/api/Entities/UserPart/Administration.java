package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.Interface.IUser;
import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "administrations")
public class Administration implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "description")
    private String description;
    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity;
    @OneToOne
    @JoinColumn(name = "place_id", nullable = false)
    private City city;
    @OneToOne
    @JoinColumn(name = "access_id", nullable = false)
    @JsonIgnore
    private Access access;

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    @JsonIncludeProperties(value = "phone")
    private List<AdministrationPhoneNumber> phones;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Administration(String name, String avatar, String description,
                          City city, Access access, OrganizerPhoneNumber phone) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.city = city;
        this.access = access;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    public Administration(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public City getCity() {
        return city;
    }

    public Access getAccess() {
        return access;
    }

    public List<AdministrationPhoneNumber> getPhones() {
        return phones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

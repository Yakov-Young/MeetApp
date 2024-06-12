package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "administrations")
public class Administration {
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
    private Place place;
    @OneToOne
    @JoinColumn(name = "access_id", nullable = false)
    @JsonIgnore
    private Access access;

    @OneToMany(mappedBy = "administration", cascade = CascadeType.ALL)
    @JsonIncludeProperties(value = "phone")
    private List<AdministrationPhoneNumber> phones;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Administration() {
    }

    public Administration(String name, String avatar, String description,
                          Place place, Access access, OrganizerPhoneNumber phone) {
        this.name = name;
        this.avatar = avatar;
        this.description = description;
        this.place = place;
        this.access = access;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    public List<AdministrationPhoneNumber> getPhones() {
        return phones;
    }

    public void setPhones(List<AdministrationPhoneNumber> phones) {
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

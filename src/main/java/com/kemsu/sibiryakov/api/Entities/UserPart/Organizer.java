package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.Interface.IUser;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "organizers")
public class Organizer implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "company")
    private String company;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday", nullable = false)
    private LocalDateTime birthday;

    @Column(name = "description")
    private String description;

    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private UserOrganizerStatus status;

    @OneToOne
    @JoinColumn(name = "access_id", nullable = false)
    @JsonIgnore
    private Access access;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<OrganizerPhoneNumber> phones;

    @OneToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ownerId")
    private List<Meet> meets;
    public Organizer(String name, String surname, String patronymic,
                     Gender gender, String company, String avatar,
                     LocalDateTime birthday, String description, UserOrganizerStatus status,
                     Access access, List<OrganizerPhoneNumber> phones, Place place) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.company = company;
        this.avatar = avatar;
        this.birthday = birthday;
        this.description = description;
        this.lastActivity = LocalDateTime.now();
        this.status = status;
        this.access = access;
        this.phones = phones;
        this.place = place;
        this.createdAt = LocalDateTime.now();
    }

    public Organizer(String name, String surname, String patronymic, String company, LocalDateTime birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.company = company;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getGender() {
        return gender.getState();
    }

    public String getCompany() {
        return company;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public UserOrganizerStatus getStatus() {
        return status;
    }

    public Access getAccess() {
        return access;
    }

    public List<OrganizerPhoneNumber> getPhones() {
        return phones;
    }

    public Place getPlace() {
        return place;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

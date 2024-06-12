package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.PlacePart.City;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false)
    private Gender gender;

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
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIncludeProperties(value = "name")
    private Role role;

    @OneToOne
    @JoinColumn(name = "access_id", nullable = false)
    @JsonIgnore
    private Access access;

    @OneToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToMany
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIncludeProperties(value = {"id", "name"})
    private Set<Category> categories;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User(String name, String surname, String patronymic,
                Gender gender, String avatar, LocalDateTime birthday,
                String description, UserOrganizerStatus status,
                Role role, Access access, City city) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.gender = gender;
        this.avatar = avatar;
        this.birthday = birthday;
        this.description = description;
        this.lastActivity = LocalDateTime.now();
        this.status = status;
        this.role = role;
        this.access = access;
        this.city = city;
        this.createdAt = LocalDateTime.now();
    }

    public User(String name, String surname, String patronymic,
                LocalDateTime birthday) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
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

    public Role getRole() {
        return role;
    }

    public Access getAccess() {
        return access;
    }

    public City getCity() {
        return city;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", birthday=" + birthday +
                ", description='" + description + '\'' +
                ", lastActivity=" + lastActivity +
                ", status=" + status +
                ", role=" + role +
                ", access=" + access +
                ", city=" + city +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.kemsu.sibiryakov.api.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<User> users;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Meet> meets;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

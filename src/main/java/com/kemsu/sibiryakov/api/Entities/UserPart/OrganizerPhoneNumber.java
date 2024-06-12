package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "organizer_phones")
public class OrganizerPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonIgnore
    private Organizer organizer;

    public OrganizerPhoneNumber(String phone, Organizer organizer) {
        this.phone = phone;
        this.organizer = organizer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                '}';
    }
}

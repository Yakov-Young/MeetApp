package com.kemsu.sibiryakov.api.Entities.UserPart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "administrations_phones")
public class AdministrationPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "administration_id", nullable = false)
    @JsonIgnore
    private Administration administration;

    public AdministrationPhoneNumber() {
    }

    public AdministrationPhoneNumber(String phone) {
        this.phone = phone;
    }

    public Administration getAdministration_id() {
        return administration;
    }

    public void setAdministration_id(Administration administration_id) {
        this.administration = administration;
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

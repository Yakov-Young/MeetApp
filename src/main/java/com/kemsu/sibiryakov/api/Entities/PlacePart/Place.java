package com.kemsu.sibiryakov.api.Entities.PlacePart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private String number;
    @Column(name = "apartment")
    private String apartment;

    public Place(City city, String street, String number, String apartment) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Places{" +
                "id=" + id +
                ", city=" + city +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}

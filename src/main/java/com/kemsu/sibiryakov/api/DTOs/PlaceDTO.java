package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {
    @JsonProperty("city")
    @NotEmpty(message = "City should not be empty")
    Long city_id;
    @JsonProperty("street")
    @NotEmpty(message = "Street should not be empty")
    String street;
    @NotEmpty(message = "Number should not be empty")
    @JsonProperty("number")
    String number;
    @NotEmpty(message = "Apartment should not be empty")
    @JsonProperty("apartment")
    String apartment;

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "city_id=" + city_id +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}

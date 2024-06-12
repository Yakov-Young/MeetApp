package com.kemsu.sibiryakov.api.DTOs.RegisterDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kemsu.sibiryakov.api.DTOs.CityDTO.CityAddOneDTO;
import com.kemsu.sibiryakov.api.DTOs.PlaceDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AdministrationRegisterDTO {
    @JsonProperty("name")
    @NotEmpty(message = "Name of administration should not be empty")
    String name;
    @NotEmpty(message = "Email of administration should not be empty")
    @JsonProperty("email")
    @Email
    String email;
    @JsonProperty("password")
    @NotEmpty(message = "Password should not be empty")
    String password;
    @JsonProperty("checkPassword")
    @NotEmpty(message = "Check password should not be empty")
    String checkPassword;
    @JsonProperty("city")
    @NotEmpty(message = "Place should not be empty")
    Long city;
    @JsonProperty("phones")
    @NotEmpty(message = "Number phone should not be empty")
    List<String> phones;

    @Override
    public String toString() {
        return "AdministrationRegisterDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }
}

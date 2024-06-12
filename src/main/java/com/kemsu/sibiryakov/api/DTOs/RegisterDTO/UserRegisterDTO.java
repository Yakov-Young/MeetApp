package com.kemsu.sibiryakov.api.DTOs.RegisterDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRegisterDTO {
    @JsonProperty("name")
    @NotEmpty(message = "Name should not be empty")
    String name;
    @JsonProperty("surname")
    @NotEmpty(message = "Surname should not be empty")
    String surname;
    @JsonProperty("patronymic")
    String patronymic;
    @JsonProperty("birthday")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @NotEmpty(message = "Birthday should not be empty")
    LocalDateTime birthday;
    @NotEmpty(message = "Email should not be empty")
    @JsonProperty("email")
    @Email
    String email;
    @JsonProperty("password")
    @NotEmpty(message = "Password should not be empty")
    String password;
    @JsonProperty("checkPassword")
    @NotEmpty(message = "Check password should not be empty")
    String checkPassword;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                '}';
    }
}

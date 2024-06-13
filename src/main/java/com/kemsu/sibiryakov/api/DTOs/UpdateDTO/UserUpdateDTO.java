package com.kemsu.sibiryakov.api.DTOs.UpdateDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
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
    LocalDateTime birthday;
    @JsonProperty("gender")
    @NotEmpty(message = "Gender should not be empty")
    String gender;
    @JsonProperty("description")
    String description;
    @JsonProperty("category")
    List<Long> category;
}

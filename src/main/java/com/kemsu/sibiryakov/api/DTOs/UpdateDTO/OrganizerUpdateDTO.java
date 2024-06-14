package com.kemsu.sibiryakov.api.DTOs.UpdateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizerUpdateDTO {
    @JsonProperty("gender")
    @NotEmpty(message = "Gender should not be empty")
    String gender;
    @JsonProperty("description")
    String description;
    @JsonProperty("phones")
    @NotEmpty(message = "Number phone should not be empty")
    List<String> phones;
}

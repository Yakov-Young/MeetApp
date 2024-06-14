package com.kemsu.sibiryakov.api.DTOs.UpdateDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AdministrationUpdateDTO {
    @JsonProperty("description")
    String description;
    @JsonProperty("phones")
    @NotEmpty(message = "Number phone should not be empty")
    List<String> phones;
}

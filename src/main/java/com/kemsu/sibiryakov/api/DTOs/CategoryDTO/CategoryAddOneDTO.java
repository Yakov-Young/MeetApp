package com.kemsu.sibiryakov.api.DTOs.CategoryDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class CategoryAddOneDTO {
    @JsonProperty("category")
    @NotEmpty
    private String name;
}

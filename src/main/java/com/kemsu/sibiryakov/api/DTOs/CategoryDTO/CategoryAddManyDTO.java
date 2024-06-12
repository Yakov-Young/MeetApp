package com.kemsu.sibiryakov.api.DTOs.CategoryDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryAddManyDTO {
    @JsonProperty("categories")
    @NotEmpty
    private List<String> name;
}

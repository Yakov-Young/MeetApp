package com.kemsu.sibiryakov.api.DTOs.RegionDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RegionAddManyDTO {
    @JsonProperty("regions")
    @NotEmpty
    private List<String> name;

}

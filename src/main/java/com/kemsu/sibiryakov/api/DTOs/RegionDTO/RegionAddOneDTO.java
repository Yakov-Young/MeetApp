package com.kemsu.sibiryakov.api.DTOs.RegionDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class RegionAddOneDTO {
    @JsonProperty("region")
    @NotEmpty
    private String name;

}

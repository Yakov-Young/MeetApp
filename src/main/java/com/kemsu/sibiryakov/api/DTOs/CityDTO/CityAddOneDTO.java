package com.kemsu.sibiryakov.api.DTOs.CityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CityAddOneDTO {
    @JsonProperty("city")
    @NotEmpty
    private String name;
    @JsonProperty("region")
    @NotEmpty
    private Long regionId;
}

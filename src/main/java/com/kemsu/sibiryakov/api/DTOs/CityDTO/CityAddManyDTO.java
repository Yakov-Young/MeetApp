package com.kemsu.sibiryakov.api.DTOs.CityDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityAddManyDTO {
    @JsonProperty("cities")
    @NotEmpty
    private List<String> name;
    @JsonProperty("region")
    @NotEmpty
    private Long regionId;

}

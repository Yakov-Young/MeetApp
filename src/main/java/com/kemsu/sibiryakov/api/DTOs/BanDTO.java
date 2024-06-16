package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanDTO {
    @JsonProperty("id")
    @NotEmpty(message = "Meet id should not empty")
    Long id;

    @JsonProperty("reason")
    @NotEmpty(message = "Reason should not empty")
    String content;
}

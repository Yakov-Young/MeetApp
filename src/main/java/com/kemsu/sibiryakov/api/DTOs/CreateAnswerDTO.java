package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAnswerDTO {
    @JsonProperty("questionId")
    @NotEmpty(message = "Meet id should not be empty")
    Long questionId;
    @JsonProperty("body")
    @NotEmpty(message = "Body id should not be empty")
    String content;
}

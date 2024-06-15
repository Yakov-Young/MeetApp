package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDTO {
    @JsonProperty("meetId")
    @NotEmpty(message = "Meet id should not be empty")
    Long meetId;
    @JsonProperty("grade")
    @NotEmpty(message = "Grade should not be empty")
    Short grade;
    @JsonProperty("body")
    String content;

}

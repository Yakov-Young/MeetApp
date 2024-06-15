package com.kemsu.sibiryakov.api.DTOs.MeetDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannedMeetDTO {
    @JsonProperty("meetId")
    @NotEmpty(message = "Meet id should not empty")
    Long id;

    @JsonProperty("reason")
    @NotEmpty(message = "Reason should not empty")
    String content;
}

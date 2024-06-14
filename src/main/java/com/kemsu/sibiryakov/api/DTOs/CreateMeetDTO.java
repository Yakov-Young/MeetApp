package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateMeetDTO {
    @JsonProperty("title")
    @NotEmpty(message = "Title should not be empty")
    String title;

    @Override
    public String toString() {
        return "CreateMeetDTO{" +
                "title='" + title + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", place=" + place +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", cost=" + cost +
                ", category=" + category +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }

    @JsonProperty("dateStart")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @NotEmpty(message = "Date start should not be empty")
    LocalDateTime dateStart;
    @JsonProperty("dateEnd")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @NotEmpty(message = "Date end should not be empty")
    LocalDateTime dateEnd;
    @JsonProperty("place")
    @NotEmpty(message = "Place should not be empty")
    PlaceDTO place;
    @JsonProperty("description")
    @NotEmpty(message = "Description should not be empty")
    String description;
    @JsonProperty("count")
    Short count;
    @JsonProperty("cost")
    Short cost;
    @JsonProperty("category")
    @NotEmpty(message = "Category should not be empty")
    List<Long> category;
    @JsonProperty("additionalInfo")
    String additionalInfo;
}

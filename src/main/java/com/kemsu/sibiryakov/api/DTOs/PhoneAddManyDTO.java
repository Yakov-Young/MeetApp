package com.kemsu.sibiryakov.api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneAddManyDTO {
    @JsonProperty("phones")
    public List<String> phones;

    @Override
    public String toString() {
        return "PhoneAddManyDTO{" +
                "phones=" + phones +
                '}';
    }
}

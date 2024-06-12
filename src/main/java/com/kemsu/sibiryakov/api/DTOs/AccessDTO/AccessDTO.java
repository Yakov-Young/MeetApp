package com.kemsu.sibiryakov.api.DTOs.AccessDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessDTO {
    public String login;
    public String password;

    public AccessDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

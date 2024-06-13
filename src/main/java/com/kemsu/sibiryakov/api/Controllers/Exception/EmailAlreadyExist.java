package com.kemsu.sibiryakov.api.Controllers.Exception;

public class EmailAlreadyExist extends Exception {
    public EmailAlreadyExist(String email) {
        super(email + "email already exists");
    }
}

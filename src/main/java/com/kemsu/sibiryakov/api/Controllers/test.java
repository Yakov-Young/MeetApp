package com.kemsu.sibiryakov.api.Controllers;

import com.kemsu.sibiryakov.api.Services.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class test {
    private final AccessService accessService;

    @Autowired
    public test(AccessService accessService) {
        this.accessService = accessService;
    }


}

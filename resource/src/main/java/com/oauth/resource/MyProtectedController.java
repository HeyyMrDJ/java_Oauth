package com.oauth.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyProtectedController {

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "This is a protected endpoint. You're authenticated!";
    }
}

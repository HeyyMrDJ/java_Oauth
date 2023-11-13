package com.oauth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private OAuth2Service oauth2Service;

    @GetMapping("/fail")
    public String fail() {
        System.out.println("TESTING ROUTE");
        return oauth2Service.makeNonauthenticatedRequest();
    }

    @GetMapping("/success")
    public String success() {
        System.out.println("TESTING ROUTE");
        return oauth2Service.makeAuthenticatedRequest();
    }

    @GetMapping("/token")
    public String token() {
        System.out.println("TESTING ROUTE");
        return oauth2Service.getAccessToken();
    }

}

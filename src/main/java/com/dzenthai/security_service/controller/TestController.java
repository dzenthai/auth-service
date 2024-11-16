package com.dzenthai.security_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user")
    public String userOnly() {
        return "Hello user";
    }

    @GetMapping("/admin")
    public String adminOnly() {
        return "Hello admin";
    }
}

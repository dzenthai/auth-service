package com.dzenthai.security_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userOnly() {
        return "Hello user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminOnly() {
        return "Hello admin";
    }
}

package com.dzenthai.security_service.controller;

import com.dzenthai.security_service.dto.UserDTO;
import com.dzenthai.security_service.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String authUser(@RequestBody UserDTO userDTO) {
        return userService.authUser(userDTO);
    }
}

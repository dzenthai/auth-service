package com.dzenthai.security_service.controller;

import com.dzenthai.security_service.dto.UserDTO;
import com.dzenthai.security_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> authUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.authUser(userDTO), HttpStatus.OK);
    }
}

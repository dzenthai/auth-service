package com.dzenthai.security_service.dto;

import lombok.Builder;


@Builder
public record UserDTO(
        String login, String password
)
{}

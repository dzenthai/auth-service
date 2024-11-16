package com.dzenthai.security_service.exception;

import lombok.Builder;
import java.time.LocalDateTime;


@Builder
public record ExceptionData(
        int status,
        String message,
        LocalDateTime timestamp
) {}

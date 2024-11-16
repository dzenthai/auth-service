package com.dzenthai.security_service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestClass {

        public static void main(String[] args) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = "B0b_Sup3rS3cur3";
            String hashedPassword = encoder.encode(rawPassword);
            System.out.println("Hashed password: " + hashedPassword);
        }
    }



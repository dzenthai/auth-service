package com.dzenthai.security_service.service;

import com.dzenthai.security_service.dto.UserDTO;
import com.dzenthai.security_service.entity.User;
import com.dzenthai.security_service.repository.UserRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder encoder;

    public UserService(UserRepo userRepo, JwtService jwtService, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsernameOrEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User %s not found: ".formatted(username))
        );
    }

    @Transactional(readOnly = true)
    public String authUser(UserDTO userDTO) {
        User user = (User) loadUserByUsername(userDTO.login());
        if (!encoder.matches(userDTO.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return jwtService.generateToken(user);
    }
}

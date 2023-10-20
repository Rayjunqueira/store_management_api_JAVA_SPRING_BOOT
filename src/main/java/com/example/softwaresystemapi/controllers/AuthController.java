package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.AuthResponseDto;
import com.example.softwaresystemapi.dtos.LoginDto;
import com.example.softwaresystemapi.models.UserModel;
import com.example.softwaresystemapi.security.JwtAuthenticationResponse;
import com.example.softwaresystemapi.security.JwtTokenProvider;
import com.example.softwaresystemapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<?> authenticateUser (@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        Optional<UserModel> user = userService.findByEmail(loginDto.getEmail());

        if (user.isPresent()) {
            AuthResponseDto responseDto = new AuthResponseDto(jwt, user.get());
            return ResponseEntity.ok(responseDto);
        } else {
            // Lidar com o caso em que o usuário não foi encontrado
            return ResponseEntity.notFound().build();
        }
    }
}
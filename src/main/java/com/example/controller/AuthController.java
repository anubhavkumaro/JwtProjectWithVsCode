package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.security.JwtUtil;
import com.example.service.AuthService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;
    
    private final AuthService authService;

    public AuthController(AuthenticationManager manager, JwtUtil jwtUtil,AuthService authService) {
        this.manager = manager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {

    	return ResponseEntity.ok(
    	        authService.login(req.getUsername(), req.getPassword())
    	    );
    }

    @GetMapping("/check-token")
    public ResponseEntity<String> checkToken(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Missing");
        }

        String token = authHeader.substring(7);

        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok("Token Valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Invalid");
        }
    }
}
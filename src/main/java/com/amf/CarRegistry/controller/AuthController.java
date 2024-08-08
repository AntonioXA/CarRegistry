package com.amf.CarRegistry.controller;

import com.amf.CarRegistry.controller.dto.JwtResponse;
import com.amf.CarRegistry.controller.dto.LoginRequest;
import com.amf.CarRegistry.controller.dto.SingUpRequest;
import com.amf.CarRegistry.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> registerUser(@RequestBody SingUpRequest signUpRequest) {
        JwtResponse jwtResponse = authenticationService.registerUser(signUpRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
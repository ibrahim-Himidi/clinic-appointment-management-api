package com.ibrahim.ClinicAppointmentManagementAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.LoginRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.RefreshTokenRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.LoginResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.RefreshTokenResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(
            @Valid @RequestBody RefreshTokenRequestDTO request) {

        return ResponseEntity.ok(authService.refresh(request));
    }
}
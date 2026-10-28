package com.ibrahim.ClinicAppointmentManagementAPI.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.LoginRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.LoginResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));
        String token = jwtService.generateToken(authentication.getName());
        return new LoginResponseDTO(token, "Bearer");
    }

}

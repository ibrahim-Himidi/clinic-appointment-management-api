package com.ibrahim.ClinicAppointmentManagementAPI.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.RegisterRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.UserAlreadyExistsException;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDTO request) {
        // 1. Email mevcut mu?
        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException(request.email());
        }
        String hashedPassword = passwordEncoder.encode(request.password());
        // User newUser = new User();
        // newUser.setEmail(request.email());
        // newUser.setRole(request.rol());
        // newUser.setPassword(hashedPassword);
    }
}
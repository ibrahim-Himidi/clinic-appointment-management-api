package com.ibrahim.ClinicAppointmentManagementAPI.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.CreateEmployeeRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.RoleEnum;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.User;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.UserAlreadyExistsException;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createEmployee(CreateEmployeeRequestDTO request) {

        // TODO 1: Rol kontrolü
        if (request.role() != RoleEnum.ROLE_DOCTOR &&
                request.role() != RoleEnum.ROLE_RECEPTIONIST) {

            throw new IllegalArgumentException("Invalid employee role");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + request.email());
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setRole(request.role());
        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);
    }
}
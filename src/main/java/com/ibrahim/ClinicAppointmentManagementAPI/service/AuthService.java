package com.ibrahim.ClinicAppointmentManagementAPI.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.LoginRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.RefreshTokenRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.LoginResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.RefreshTokenResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.RefreshToken;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.User;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.UserRepository;
import com.ibrahim.ClinicAppointmentManagementAPI.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new LoginResponseDTO(accessToken, refreshToken, "Bearer");
    }


    @Transactional 
    public RefreshTokenResponseDTO refresh(RefreshTokenRequestDTO request) {

        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.refreshToken());

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user.getEmail());
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new RefreshTokenResponseDTO(accessToken, newRefreshToken, "Bearer");
    }

}

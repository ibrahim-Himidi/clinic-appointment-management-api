package com.ibrahim.ClinicAppointmentManagementAPI.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.RefreshToken;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.User;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.InvalidRefreshTokenException;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    static final SecureRandom secureRandom = new SecureRandom();

    public String generateRefreshToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        return token;
    }

    public String hashToken(String token) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm not available", e);
        }
    }

    public String createRefreshToken(User user) {

        String token = generateRefreshToken();
        String hashedToken = hashToken(token);

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(user.getId());
        RefreshToken refreshToken = existingToken.orElseGet(RefreshToken::new);

        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashedToken);
        refreshToken.setRevoked(false);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiresAt(
                Instant.now().plus(7, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public RefreshToken validateRefreshToken(String token) {

        String hashedToken = hashToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(hashedToken)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

        if (refreshToken.isRevoked())
            throw new InvalidRefreshTokenException("this token is revoked");
        if (!refreshToken.getExpiresAt().isAfter(Instant.now()))
            throw new InvalidRefreshTokenException("Refresh token expired");

        return refreshToken;
    }

}
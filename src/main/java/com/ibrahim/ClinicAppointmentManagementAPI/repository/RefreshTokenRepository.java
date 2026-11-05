package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.RefreshToken;

public interface RefreshTokenRepository extends   JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);
    Optional<RefreshToken> findByUserId(Long userId);
} 
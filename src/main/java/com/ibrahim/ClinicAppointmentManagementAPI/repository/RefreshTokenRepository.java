package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.RefreshToken;

import jakarta.persistence.LockModeType;

public interface RefreshTokenRepository extends   JpaRepository<RefreshToken, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    Optional<RefreshToken> findByUserId(Long userId);
} 
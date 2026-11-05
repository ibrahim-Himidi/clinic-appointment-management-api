package com.ibrahim.ClinicAppointmentManagementAPI.dto.response;

public record RefreshTokenResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType) {
}
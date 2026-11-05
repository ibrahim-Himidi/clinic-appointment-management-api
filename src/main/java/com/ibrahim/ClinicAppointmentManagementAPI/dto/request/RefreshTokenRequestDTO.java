package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(
    @NotBlank 
        String refreshToken
) {
}
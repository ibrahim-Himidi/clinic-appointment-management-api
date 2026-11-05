package com.ibrahim.ClinicAppointmentManagementAPI.dto.response;

public record LoginResponseDTO(
                String accessToken,
                String refreshToken,
                String tokenType) {
}
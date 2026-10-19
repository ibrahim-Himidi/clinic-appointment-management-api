package com.ibrahim.ClinicAppointmentManagementAPI.dto.response;

public record DoctorResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String specialization,
        String email
) {
}
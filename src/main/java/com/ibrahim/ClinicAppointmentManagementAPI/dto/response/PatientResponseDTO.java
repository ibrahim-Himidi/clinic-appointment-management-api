package com.ibrahim.ClinicAppointmentManagementAPI.dto.response;

import java.time.LocalDate;

public record PatientResponseDTO(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    LocalDate birthDate
) {
}
package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DoctorPatchDTO(
        @Size(min = 2, max = 50)
        String firstName,

        @Size(min = 2, max = 50)
        String lastName,

        String specialization,

        @Email(message = "Invalid email format")
        String email

) {
}
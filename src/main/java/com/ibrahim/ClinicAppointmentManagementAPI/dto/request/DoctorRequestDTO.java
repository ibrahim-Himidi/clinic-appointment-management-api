package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DoctorRequestDTO(

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "Specialization is required")
        String specialization,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email

) {
}
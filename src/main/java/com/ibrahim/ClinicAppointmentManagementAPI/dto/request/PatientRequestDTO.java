package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatientRequestDTO(
        @NotBlank(message = "First name is required") 
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") 
        
        String firstName,

        @NotBlank(message = "Last name is required") 
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") 
        String lastName,

        @Email(message = "Invalid email format") 
        String email,

        @NotBlank(message = "Phone number is required") 
        @Pattern(regexp = "^(?:0|\\+90)5[0-9]{9}$", message = "Invalid phone number format. Expected format: 05xxxxxxxxx or +905xxxxxxxxx") 
        String phoneNumber,

        @NotNull(message = "Birth date is required") 
        @Past(message = "Birth date must be in the past") LocalDate birthDate) {
}
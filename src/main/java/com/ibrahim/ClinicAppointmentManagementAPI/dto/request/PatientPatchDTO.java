package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record PatientPatchDTO(
                @Pattern(regexp = "^(?!\\s*$).{2,50}$", 
                        message = "İsim sadece boşluklardan oluşamaz ve 2-50 karakter arasında olmalıdır.") 
                String firstName,

                @Pattern(regexp = "^(?!\\s*$).{2,50}$", 
                        message = "İsim sadece boşluklardan oluşamaz ve 2-50 karakter arasında olmalıdır.") 
                String lastName,

                @Email(message = "Invalid email format") String email,

                @Pattern(regexp = "^(?:0|\\+90)5[0-9]{9}$", message = "Invalid phone number format. Expected format: 05xxxxxxxxx or +905xxxxxxxxx") String phoneNumber,

                @Past(message = "Birth date must be in the past") LocalDate birthDate) {
}
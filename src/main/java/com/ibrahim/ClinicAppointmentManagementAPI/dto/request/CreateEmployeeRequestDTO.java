package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequestDTO(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @NotNull  RoleEnum role
) {
}
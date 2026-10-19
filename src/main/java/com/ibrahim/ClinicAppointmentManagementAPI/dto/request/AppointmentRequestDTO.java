package com.ibrahim.ClinicAppointmentManagementAPI.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AppointmentRequestDTO(
    @NotNull 
    Long patientId,
    @NotNull 
    Long doctorId,
    @NotNull 
    @Future 
    LocalDateTime appointmentDate
) {   
}
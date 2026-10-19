package com.ibrahim.ClinicAppointmentManagementAPI.dto.response;

import java.time.LocalDateTime;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.AppointmentStatusEnum;

public record AppointmentResponseDTO(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentDate,
        AppointmentStatusEnum status) {
}
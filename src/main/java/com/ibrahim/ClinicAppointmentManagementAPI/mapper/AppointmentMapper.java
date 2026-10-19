package com.ibrahim.ClinicAppointmentManagementAPI.mapper;

import org.springframework.stereotype.Component;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.AppointmentResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Appointment;

@Component 
public class AppointmentMapper {

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus()
        );
    }
}
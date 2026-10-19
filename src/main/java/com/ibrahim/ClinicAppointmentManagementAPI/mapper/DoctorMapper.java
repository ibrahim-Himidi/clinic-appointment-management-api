package com.ibrahim.ClinicAppointmentManagementAPI.mapper;

import org.springframework.stereotype.Component;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.DoctorRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.DoctorResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Doctor;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequestDTO request) {
        // RequestDTO → Doctor\
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        doctor.setEmail(request.email());
        doctor.setSpecialization(request.specialization());
        return doctor;
    }

    public DoctorResponseDTO toResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
            doctor.getId(),
            doctor.getFirstName(),
            doctor.getLastName(),
            doctor.getSpecialization(),
            doctor.getEmail()
        );
    }
}

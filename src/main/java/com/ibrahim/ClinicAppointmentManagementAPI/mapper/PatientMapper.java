package com.ibrahim.ClinicAppointmentManagementAPI.mapper;

import org.springframework.stereotype.Component;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.PatientRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.PatientResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Patient;

@Component 
public class PatientMapper {
    public Patient toEntity(PatientRequestDTO dto){
        Patient patient = new Patient();

        patient.setFirstName(dto.firstName());
        patient.setLastName(dto.lastName());
        patient.setEmail(dto.email());
        patient.setPhoneNumber(dto.phoneNumber());
        patient.setBirthDate(dto.birthDate());

        return patient;
    }


    public PatientResponseDTO toResponseDTO(Patient patient){
        return new PatientResponseDTO(
            patient.getId(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getEmail(),
            patient.getPhoneNumber(),
            patient.getBirthDate()
        );
        
    }
}
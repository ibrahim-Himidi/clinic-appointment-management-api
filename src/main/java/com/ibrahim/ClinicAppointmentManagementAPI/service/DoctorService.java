package com.ibrahim.ClinicAppointmentManagementAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.DoctorRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.DoctorResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Doctor;
import com.ibrahim.ClinicAppointmentManagementAPI.mapper.DoctorMapper;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.DoctorRepository;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(
            DoctorRepository doctorRepository,
            DoctorMapper doctorMapper) {

        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public DoctorResponseDTO createDoctor(DoctorRequestDTO request) {

    Doctor doctor = doctorRepository.save(doctorMapper.toEntity(request));
    return doctorMapper.toResponseDTO(doctor);
}

}
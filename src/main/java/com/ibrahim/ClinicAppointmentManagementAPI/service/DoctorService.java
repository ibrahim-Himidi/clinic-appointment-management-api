package com.ibrahim.ClinicAppointmentManagementAPI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.DoctorPatchDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.DoctorRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.DoctorResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Doctor;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.DoctorNotFoundException;
import com.ibrahim.ClinicAppointmentManagementAPI.mapper.DoctorMapper;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.AppointmentRepository;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.DoctorRepository;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(
            DoctorRepository doctorRepository,
            DoctorMapper doctorMapper,
            AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.appointmentRepository = appointmentRepository;
    }

    public DoctorResponseDTO createDoctor(DoctorRequestDTO request) {

        Doctor doctor = doctorRepository.save(doctorMapper.toEntity(request));
        return doctorMapper.toResponseDTO(doctor);
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        List<Doctor> allDoctors = doctorRepository.findAll();
        List<DoctorResponseDTO> dtoList = new ArrayList<>();

        for (Doctor doctor : allDoctors) {
            dtoList.add(doctorMapper.toResponseDTO(doctor));
        }
        return dtoList;
    }

    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        return doctorMapper.toResponseDTO(doctor);
    }

    @Transactional
    public DoctorResponseDTO updateDoctor(Long id, DoctorPatchDTO request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        if (request.firstName() != null)
            doctor.setFirstName(request.firstName());
        if (request.lastName() != null)
            doctor.setLastName(request.lastName());
        if (request.email() != null)
            doctor.setEmail(request.email());
        if (request.specialization() != null)
            doctor.setSpecialization(request.specialization());
        return doctorMapper.toResponseDTO(doctor);
    }

    @Transactional
    public void deleteById(Long id) {
        doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        if (appointmentRepository.existsByDoctorId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete doctor because they have existing appointments");
        }
        doctorRepository.deleteById(id);

    }

}
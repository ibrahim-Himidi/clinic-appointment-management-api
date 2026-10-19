package com.ibrahim.ClinicAppointmentManagementAPI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.PatientPatchDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.PatientRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.PatientResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Patient;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.PatientNotFoundException;
import com.ibrahim.ClinicAppointmentManagementAPI.mapper.PatientMapper;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.PatientRepository;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper mapper;

    public PatientService(PatientRepository patientRepository, PatientMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public PatientResponseDTO savePatient(PatientRequestDTO requestDTO) {
        Patient patient = mapper.toEntity(requestDTO);
        return mapper.toResponseDTO(patientRepository.save(patient));
    }

    public PatientResponseDTO getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        return mapper.toResponseDTO(patient);
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> allPatients = patientRepository.findAll();
        List<PatientResponseDTO> dtoList = new ArrayList<>();

        for (Patient patient : allPatients) {
            dtoList.add(mapper.toResponseDTO(patient));
        }
        return dtoList;
    }


    public void deletePatient(Long id) {
        if (patientRepository.existsById(id))
            patientRepository.deleteById(id);
        else
            throw new PatientNotFoundException("Patient not found with id: " + id);
    }

    public PatientResponseDTO updatePatient(
            Long id,
            PatientRequestDTO requestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        patient.setFirstName(requestDTO.firstName());
        patient.setLastName(requestDTO.lastName());
        patient.setPhoneNumber(requestDTO.phoneNumber());
        patient.setEmail(requestDTO.email());
        patient.setBirthDate(requestDTO.birthDate());
        Patient updatedPatient = patientRepository.save(patient);
        return mapper.toResponseDTO(updatedPatient);
    }

    public PatientResponseDTO patchPatient(
            Long id,
            PatientPatchDTO requestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        if (requestDTO.firstName() != null)
            patient.setFirstName(requestDTO.firstName());
        if (requestDTO.lastName() != null)
            patient.setLastName(requestDTO.lastName());
        if (requestDTO.email() != null)
            patient.setEmail(requestDTO.email());
        if (requestDTO.phoneNumber() != null)
            patient.setPhoneNumber(requestDTO.phoneNumber());
        if (requestDTO.birthDate() != null)
            patient.setBirthDate(requestDTO.birthDate());

        Patient updatedPatient = patientRepository.save(patient);

        return mapper.toResponseDTO(updatedPatient);
    }

}
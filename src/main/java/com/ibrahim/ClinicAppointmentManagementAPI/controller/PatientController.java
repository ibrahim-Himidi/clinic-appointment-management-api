package com.ibrahim.ClinicAppointmentManagementAPI.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.PatientPatchDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.PatientRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.PatientResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.service.PatientService;

import jakarta.validation.Valid; 
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO requestDTO) {

        PatientResponseDTO patient = patientService.savePatient(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById( @PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id,@Valid @RequestBody PatientRequestDTO requestDTO) {
        return ResponseEntity.ok( patientService.updatePatient(id, requestDTO));
    }


    @PatchMapping("/{id}")
    public ResponseEntity <PatientResponseDTO> patchPatient( @PathVariable Long id ,@Valid @RequestBody PatientPatchDTO dto){
        return ResponseEntity.ok(patientService.patchPatient(id, dto));
    }
}
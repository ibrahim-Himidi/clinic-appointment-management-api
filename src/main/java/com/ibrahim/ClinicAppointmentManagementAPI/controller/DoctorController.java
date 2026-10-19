package com.ibrahim.ClinicAppointmentManagementAPI.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.DoctorRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.DoctorResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController 
@RequestMapping ("/api/doctors")
public class DoctorController {
 private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createNewDoctor(
        @Valid @RequestBody DoctorRequestDTO request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(doctorService.createDoctor(request));
    }

    
}
package com.ibrahim.ClinicAppointmentManagementAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.AppointmentRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.AppointmentResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Appointment;
import com.ibrahim.ClinicAppointmentManagementAPI.service.AppointmentService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> cretaeAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(requestDTO));
    }

}
package com.ibrahim.ClinicAppointmentManagementAPI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.AppointmentRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.AppointmentResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.service.AppointmentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(
            @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(
            @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponseDTO> completeAppointment(
            @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.completeAppointment(id));
    }



}
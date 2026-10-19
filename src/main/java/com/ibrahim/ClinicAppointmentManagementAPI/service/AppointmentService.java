package com.ibrahim.ClinicAppointmentManagementAPI.service;

import com.ibrahim.ClinicAppointmentManagementAPI.dto.request.AppointmentRequestDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.dto.response.AppointmentResponseDTO;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Appointment;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.AppointmentStatusEnum;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Doctor;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.Patient;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.AppointmentNotFoundException;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.DoctorNotFoundException;
import com.ibrahim.ClinicAppointmentManagementAPI.exception.PatientNotFoundException;
import com.ibrahim.ClinicAppointmentManagementAPI.mapper.AppointmentMapper;
import com.ibrahim.ClinicAppointmentManagementAPI.mapper.PatientMapper;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.AppointmentRepository;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.DoctorRepository;
import com.ibrahim.ClinicAppointmentManagementAPI.repository.PatientRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class AppointmentService {

        private final AppointmentRepository appointmentRepository;
        private final PatientRepository patientRepository;
        private final DoctorRepository doctorRepository;
        private final AppointmentMapper appointmentMapper;
        private final PatientMapper patientMapper;

        public AppointmentService(
                        AppointmentRepository appointmentRepository,
                        PatientRepository patientRepository,
                        DoctorRepository doctorRepository,
                        AppointmentMapper appointmentMapper, PatientMapper patientMapper) {
                this.appointmentRepository = appointmentRepository;
                this.patientRepository = patientRepository;
                this.doctorRepository = doctorRepository;
                this.appointmentMapper = appointmentMapper;
                this.patientMapper = patientMapper;
        }

        private boolean isWorkingDay(LocalDate date) {

                DayOfWeek day = date.getDayOfWeek();

                return day != DayOfWeek.SATURDAY
                                && day != DayOfWeek.SUNDAY;
        }

        private boolean isValidAppointmentTime(LocalTime time) {
                boolean morning = !time.isBefore(LocalTime.of(9, 0))
                                && !time.isAfter(LocalTime.of(11, 30));

                boolean afternoon = !time.isBefore(LocalTime.of(13, 30))
                                && !time.isAfter(LocalTime.of(16, 30));

                boolean validMinute = time.getMinute() == 0 || time.getMinute() == 30;

                return (morning || afternoon)
                                && validMinute
                                && time.getSecond() == 0
                                && time.getNano() == 0;
        }

        private boolean isSlotAvailable(
                        Long doctorId,
                        LocalDateTime appointmentDate) {
                return !appointmentRepository
                                .existsByDoctorIdAndAppointmentDateAndStatusNot(
                                                doctorId,
                                                appointmentDate,
                                                AppointmentStatusEnum.CANCELLED);
        }

        public AppointmentResponseDTO createAppointment(
                        AppointmentRequestDTO request) {

                Patient patient = patientRepository.findById(request.patientId())
                                .orElseThrow(() -> new PatientNotFoundException(
                                                "Patient not found with id: " + request.patientId()));
                Doctor doctor = doctorRepository.findById(request.doctorId())
                                .orElseThrow(() -> new DoctorNotFoundException(
                                                "Doctor not found with id: " + request.doctorId()));

                LocalDateTime appointmentDate = request.appointmentDate();
                if (!isWorkingDay(appointmentDate.toLocalDate())) {
                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Doctor does not work on this day");
                }

                if (!isValidAppointmentTime(appointmentDate.toLocalTime())) {
                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Invalid appointment time");
                }

                if (!isSlotAvailable(doctor.getId(), appointmentDate)) {
                        throw new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Appointment slot is already booked");
                }
                Appointment appointment = new Appointment();
                appointment.setDoctor(doctor);
                appointment.setPatient(patient);
                appointment.setAppointmentDate(appointmentDate);
                appointment.setStatus(AppointmentStatusEnum.SCHEDULED);
                Appointment newAppointment = appointmentRepository.save(appointment);
                return appointmentMapper.toResponseDTO(newAppointment);
        }

        public List<AppointmentResponseDTO> getAllAppointments() {

                List<Appointment> appointmentsList = appointmentRepository.findAll();
                List<AppointmentResponseDTO> appointmentResponseDTOs = new ArrayList<>();
                for (Appointment appointment : appointmentsList) {
                        appointmentResponseDTOs.add(appointmentMapper.toResponseDTO(
                                        appointment));
                }
                return appointmentResponseDTOs;
        }

        public AppointmentResponseDTO getAppointmentById(Long id) {

        // 1. appointmentRepository.findById(id)
        Appointment appointement= appointmentRepository.findById(id).
                orElseThrow(()-> new AppointmentNotFoundException("Appointement not found with id: " + id));

        return appointmentMapper.toResponseDTO(appointement);
}

}
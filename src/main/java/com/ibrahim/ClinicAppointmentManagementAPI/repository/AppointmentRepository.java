package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.Appointment;
import com.ibrahim.ClinicAppointmentManagementAPI.entity.AppointmentStatusEnum;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
        boolean existsByDoctorIdAndAppointmentDate(
                        Long doctorId,
                        LocalDateTime appointmentDate);

        boolean existsByDoctorIdAndAppointmentDateAndStatusNot(
                        Long doctorId,
                        LocalDateTime appointmentDate,
                        AppointmentStatusEnum status);

        boolean existsByDoctorId(Long doctorId);
}
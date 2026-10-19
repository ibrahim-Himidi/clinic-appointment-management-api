package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
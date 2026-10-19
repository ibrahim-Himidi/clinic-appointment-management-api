package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
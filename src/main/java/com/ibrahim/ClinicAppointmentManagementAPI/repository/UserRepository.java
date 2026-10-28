package com.ibrahim.ClinicAppointmentManagementAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibrahim.ClinicAppointmentManagementAPI.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String emial);

    Boolean exexistsByEmail(String email);
}
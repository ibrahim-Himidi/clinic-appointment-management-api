package com.ibrahim.ClinicAppointmentManagementAPI.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Setter 
@Getter 
@NoArgsConstructor 
@Table(name = "patients")
public class Patient {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;
    
    @Column(name = "email" , nullable = true)
    private String email;
    
    @Column(name = "phone_number" , nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date" , nullable = false)
    private LocalDate birthDate;
}
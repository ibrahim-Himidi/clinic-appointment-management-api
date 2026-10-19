package com.ibrahim.ClinicAppointmentManagementAPI.entity;

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
@Table (name = "doctors")
@Getter 
@Setter 
@NoArgsConstructor 
public class Doctor {
    @Id 
    @Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name" , nullable = false)
    private String firstName;
    @Column(name = "last_name" , nullable = false)
    private String lastName;
    @Column(name = "specialization" , nullable = false)
    private String specialization;
    @Column(name = "email" , nullable = false ,  unique = true)
    private  String email;
    @Column(nullable = false)
    private boolean active = true;

}
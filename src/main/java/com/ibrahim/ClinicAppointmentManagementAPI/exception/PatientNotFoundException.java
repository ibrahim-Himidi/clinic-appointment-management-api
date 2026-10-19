package com.ibrahim.ClinicAppointmentManagementAPI.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);

    }
}
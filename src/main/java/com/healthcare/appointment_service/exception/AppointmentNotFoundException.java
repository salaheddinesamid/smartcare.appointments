package com.healthcare.appointment_service.exception;

public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException(Integer appointmentId) {
        super("Appointment with id: "+ appointmentId+ " cannot be found");
    }
}

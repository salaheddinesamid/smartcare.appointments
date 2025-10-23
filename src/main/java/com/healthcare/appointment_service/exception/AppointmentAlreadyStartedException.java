package com.healthcare.appointment_service.exception;

public class AppointmentAlreadyStartedException extends RuntimeException{

    public AppointmentAlreadyStartedException(Integer appointmentId){
        super("Appointment with id :" + appointmentId + " has already been started");
    }
}

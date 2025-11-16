package com.healthcare.appointment_service.service;

public interface AppointmentCanceler {
    /**
     * This method handles appointment cancellation
     * @param appointmentId
     */
    void cancelAppointment(Integer appointmentId);
}

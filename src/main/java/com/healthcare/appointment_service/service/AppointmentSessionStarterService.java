package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentSessionDto;

public interface AppointmentSessionStarterService {

    /**
     * This method is responsible for starting an appointment session and update the countdown of the session  in real-time.
     * @param appointmentId
     * @return new details about the appointment session.
     */
    AppointmentSessionDto startSession(Integer appointmentId);

    /**
     * This method is responsible for completing an appointment session.
     * @param appointmentId
     * @return new details about the appointment session.
     */
    AppointmentSessionDto completeSession(Integer appointmentId);
}

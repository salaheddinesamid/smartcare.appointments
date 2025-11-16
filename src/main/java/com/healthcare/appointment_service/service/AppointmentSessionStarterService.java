package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentSessionDto;

public interface AppointmentSessionStarterService {

    /**
     *
     * @param appointmentId
     * @return
     */
    AppointmentSessionDto startSession(Integer appointmentId);

    /**
     *
     * @param appointmentId
     * @return
     */
    AppointmentSessionDto completeSession(Integer appointmentId);
}

package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;

import java.time.LocalDateTime;

public interface AppointmentRescheduleService {

    /**
     * Thi function calls an external API to check if the doctor is available or not.
     * @param appointmentId
     * @param newDate
     * @return a boolean
     */
    NewAppointmentResponseDTO rescheduleAppointment(Integer appointmentId, LocalDateTime newDate);
}

package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;

public interface AppointmentScheduleService {

    /**
     * This function handles business logic of appointment scheduling
     * @param requestDTO
     * @return a response containing new appointment details
     */
    NewAppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO requestDTO);
}

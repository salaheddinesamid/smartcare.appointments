package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentResponseDto;

import java.util.List;

public interface AppointmentQueryService {

    /**
     *
     * @return
     */
    List<AppointmentResponseDto> getAll();

    /**
     *
     * @param doctorId
     * @return
     */
    List<AppointmentResponseDto> getAllDoctorAppointments(Integer doctorId);
}

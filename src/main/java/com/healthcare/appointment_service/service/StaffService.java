package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.DoctorDto;
import com.healthcare.appointment_service.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StaffService {

    /**
     *
     * @param doctorId
     * @param dateTime
     * @return
     */
    boolean checkDoctorAvailability(Integer doctorId, LocalDateTime dateTime);

    /**
     *
     * @param doctorId
     * @return
     */
    boolean checkDoctorExistence(Integer doctorId);

    /**
     *
     * @param doctorId
     * @return
     */
    DoctorDto getDoctor(Integer doctorId);

    /**
     *
     * @param ids
     * @return
     */
    List<DoctorDto> getDoctors(List<Integer> ids);
}

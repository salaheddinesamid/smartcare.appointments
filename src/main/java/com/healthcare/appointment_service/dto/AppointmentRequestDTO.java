package com.healthcare.appointment_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {

    private Integer patientId;
    private Integer doctorId;
    private String appointmentType;
    private LocalDateTime startDate;
}

package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreatedEvent {

    private Integer appointmentId;
    private Integer patientId;
    private LocalDateTime createdAt;
    private double price;

    public AppointmentCreatedEvent(Appointment appointment){
        this.appointmentId = appointment.getAppointmentId();
        this.patientId = appointment.getPatientId();
        this.createdAt = LocalDateTime.now();
        this.price = 100;
    }
}

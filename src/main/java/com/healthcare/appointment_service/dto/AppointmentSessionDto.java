package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentSessionDto {

    private Integer appointmentId;
    private boolean started;
    private boolean completed;
    private float duration;
    private String status;

    public AppointmentSessionDto(
            Appointment appointment
    ){
        this.appointmentId = appointment.getAppointmentId();
        this.started = true;
        this.duration = appointment.getDuration();
        this.status = appointment.getStatus().toString();
        this.completed = appointment.getStatus().equals(AppointmentStatus.COMPLETED);
    }
}

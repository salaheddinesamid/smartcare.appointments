package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAppointmentResponseDTO {
    private Integer appointmentId;
    private Integer patientId;
    private Integer doctorId;
    private String disease;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String status;

    public NewAppointmentResponseDTO(Appointment appointment){
        this.appointmentId = appointment.getAppointmentId();
        this.patientId = appointment.getPatientId();
        this.doctorId = appointment.getDoctorId();
        this.disease = appointment.getDisease().toString();
        this.startDate = appointment.getStartDate();
        this.endDate = appointment.getEndDate();
        this.type = appointment.getAppointmentType().toString();
        this.status = appointment.getStatus().toString();
    }
}

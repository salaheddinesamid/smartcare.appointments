package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NewPrescriptionResponseDto {
    private Integer doctorId;
    private Integer patientId;
    private Integer appointmentId;
    private LocalDate validUntil;
    private LocalDateTime issueDate;

    public NewPrescriptionResponseDto(
            Prescription prescription
    ){
        this.doctorId = prescription.getDoctorId();
        this.patientId = prescription.getPatientId();
        this.appointmentId = prescription.getAppointment().getAppointmentId();
        this.issueDate = prescription.getIssueDate();
        this.validUntil = prescription.getValidUntil();
    }
}

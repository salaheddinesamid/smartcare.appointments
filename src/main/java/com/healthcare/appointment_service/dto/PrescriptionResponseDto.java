package com.healthcare.appointment_service.dto;
import com.healthcare.appointment_service.model.Prescription;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionResponseDto {
    private Integer doctorId;
    private Integer patientId;
    private Integer appointmentId;
    private LocalDate validUntil;
    private LocalDateTime issueDate;
    private List<PrescriptionItemResponseDto> details;


    public PrescriptionResponseDto(
            Prescription prescription
    ){
        this.doctorId = prescription.getDoctorId();
        this.patientId = prescription.getPatientId();
        this.appointmentId = prescription.getAppointment().getAppointmentId();
        this.validUntil = prescription.getValidUntil();
        this.issueDate = prescription.getIssueDate();
        this.details =
                prescription.getDetails().stream()
                        .map(PrescriptionItemResponseDto::new).toList();
    }
}



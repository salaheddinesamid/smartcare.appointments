package com.healthcare.appointment_service.dto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NewPrescriptionDto {
    private Integer doctorId;
    private Integer patientId;
    private Integer appointmentId;
    private LocalDate validUntil;
    private List<PrescriptionDetailDto> details;
}

@Data
class PrescriptionDetail{
    private Integer medicineId;
    private String dosage;
    private Integer frequency;
    private String route;
    private Integer duration;
    private String instructions;
}




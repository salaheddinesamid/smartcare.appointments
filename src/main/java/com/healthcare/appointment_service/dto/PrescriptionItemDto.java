package com.healthcare.appointment_service.dto;

import lombok.Data;

@Data
public class PrescriptionItemDto {
    private Integer medicineId;
    private String dosage;
    private Integer frequency;
    private String route;
    private Integer duration;
    private String instructions;
}

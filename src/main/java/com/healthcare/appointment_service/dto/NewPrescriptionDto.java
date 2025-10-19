package com.healthcare.appointment_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewPrescriptionDto {
    private LocalDate validUntil;
    private List<PrescriptionItemDto> details;
}

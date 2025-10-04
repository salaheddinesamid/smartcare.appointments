package com.healthcare.appointment_service.dto;

import lombok.Data;

@Data
public class PrescriptionValidityResponseDto {

    private boolean validity;
    private PrescriptionResponseDto details;

    public PrescriptionValidityResponseDto(
            boolean validity,
            PrescriptionResponseDto details
    ){
        this.validity = validity;
        this.details = details;
    }
}

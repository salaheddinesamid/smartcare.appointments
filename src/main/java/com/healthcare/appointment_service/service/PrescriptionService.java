package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.NewPrescriptionDto;
import com.healthcare.appointment_service.dto.NewPrescriptionResponseDto;
import com.healthcare.appointment_service.dto.PrescriptionValidityResponseDto;

public interface PrescriptionService {

    /**
     *
     */
    NewPrescriptionResponseDto createPrescription(Integer appointmentId, NewPrescriptionDto newPrescriptionDto);

    /**
     * This method verifies whether a prescription is valid or not
     * @param refNumber
     * @return
     */
    PrescriptionValidityResponseDto checkPrescriptionValidity(String refNumber);
}

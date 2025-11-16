package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.PatientDto;

import java.util.List;

public interface PatientServiceClient {

    boolean checkPatientExistence(String nationalId);
    List<PatientDto> getPatients(List<Integer> ids);
    PatientDto getPatient(Integer patientId);
    List<AppointmentResponseDto> getAllPatientAppointment(Integer patientId);
}

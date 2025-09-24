package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.AppointmentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface AppointmentService {

    /**
     * This method schedules new appointment
     * @param appointmentRequestDTO
     * @return
     */
    public ResponseEntity<?> scheduleAppointment(AppointmentRequestDTO appointmentRequestDTO);

    /**
     * This method is used to reschedule an appointment
     */
    public ResponseEntity<?> rescheduleAppointment(Integer appointmentId, LocalDateTime newDate);

    /**
     * This method returns all the previous and next appointments of the patient
     * @param patientId
     */
    public ResponseEntity<?> getAllPatientAppointments(Integer patientId);
    public getAllDoctorAppointments();
    public scheduleNextPatient();
    public createMedicalRecord();
}

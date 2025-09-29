package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    /**
     * This method schedules new appointment
     * @param appointmentRequestDTO
     * @return
     */
    NewAppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO appointmentRequestDTO);

    /**
     * This method is used to reschedule an appointment
     */
    NewAppointmentResponseDTO rescheduleAppointment(Integer appointmentId, LocalDateTime newDate);

    /**
     * This method returns all the previous and next appointments of the patient
     * @param patientId
     */
    public ResponseEntity<?> getAllPatientAppointments(Integer patientId);

    /**
     * This method returns all the appointments related to a doctor
     */
    public List<AppointmentResponseDto> getAllDoctorAppointments();

    /**
     * This method used by a doctor to schedule next appointment for the patient
     * @param doctorId
     * @param patientId
     */
    public AppointmentResponseDto scheduleNextAppointment(Integer doctorId, Integer patientId);

    /**
     *
     */
    public ResponseEntity<?> createPrescription();
}

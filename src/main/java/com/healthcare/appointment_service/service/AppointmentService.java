package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.dto.*;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.Prescription;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
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
    List<AppointmentResponseDto> getAllPatientAppointments(Integer patientId);

    List<AppointmentResponseDto> getPatientAppointmentsByDate(Integer patientId, LocalDate date);

    /**
     * This method returns all the appointments related to a doctor
     */
    List<AppointmentResponseDto> getAllDoctorAppointments(Integer doctorId);

    List<AppointmentResponseDto> getDoctorAppointmentsByDate(Integer doctorId, LocalDate date);

    /**
     * This method used by a doctor to schedule next appointment for the patient
     * @param doctorId
     * @param patientId
     */
    AppointmentResponseDto scheduleNextAppointment(Integer doctorId, Integer patientId);

    /**
     *
     */
    NewPrescriptionResponseDto createPrescription(Integer appointmentId,NewPrescriptionDto newPrescriptionDto);

    /**
     *
     */

    List<AppointmentResponseDto> getAll();

    /**
     * This function is responsible for starting appointments
     * @param appointmentId
     * @return
     */
    AppointmentSessionDto startAppointmentSession(Integer appointmentId);

    /**
     * This function handles completing an appointment session
     * @param appointmentId
     * @return
     */
    AppointmentSessionDto completeAppointmentSession(Integer appointmentId);

    /**
     * This method verifies whether a prescription is valid or not
     * @param refNumber
     * @return
     */
    PrescriptionValidityResponseDto checkPrescriptionValidity(String refNumber);
}

package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.AppointmentResponseDTO;
import com.healthcare.appointment_service.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Override
    public ResponseEntity<?> scheduleAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> rescheduleAppointment(Integer appointmentId, LocalDateTime newDate) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllPatientAppointments(Integer patientId) {
        return null;
    }

    @Override
    public List<AppointmentResponseDTO> getAllDoctorAppointments() {
        return List.of();
    }

    @Override
    public AppointmentResponseDTO scheduleNextAppointment(Integer doctorId, Integer patientId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createPrescription() {
        return null;
    }
}

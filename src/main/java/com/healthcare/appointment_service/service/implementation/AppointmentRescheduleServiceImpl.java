package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.exception.AppointmentNotFoundException;
import com.healthcare.appointment_service.exception.UnavailableDoctorException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentRescheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentRescheduleServiceImpl implements AppointmentRescheduleService {

    private final AppointmentRepository appointmentRepository;
    private final StaffServiceImpl staffService;

    @Autowired
    public AppointmentRescheduleServiceImpl(AppointmentRepository appointmentRepository, StaffServiceImpl staffService) {
        this.appointmentRepository = appointmentRepository;
        this.staffService = staffService;
    }

    @Override
    public NewAppointmentResponseDTO rescheduleAppointment(Integer appointmentId, LocalDateTime newDate) {
        // Fetch the appointment
        Appointment appointment = appointmentRepository
                .findById(appointmentId).orElseThrow(()-> new AppointmentNotFoundException(appointmentId));

        // Check if the doctor is available
        boolean isDoctorAvailable = staffService.checkDoctorAvailability(appointment.getDoctorId(),newDate);
        if(!isDoctorAvailable){
            throw new UnavailableDoctorException("");
        }

        appointment.setStartDate(newDate);

        // save the appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new NewAppointmentResponseDTO(
                savedAppointment
        );
    }
}

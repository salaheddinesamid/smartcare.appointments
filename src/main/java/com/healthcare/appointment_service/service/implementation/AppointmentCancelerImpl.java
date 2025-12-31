package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentStatus;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentCanceler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentCancelerImpl implements AppointmentCanceler {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentCancelerImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void cancelAppointment(Integer appointmentId) {
        // Fetch the appointment from db:
        Appointment appointment = appointmentRepository
                .findById(appointmentId).orElseThrow();

        // Check if the appointment is not already started or there is at most 24 hours before starting date:
        if(appointment.getStatus().equals(AppointmentStatus.ON_GOING)){

        }

        appointment.setStatus(AppointmentStatus.CANCELED);

        // Cancel the invoice:
        // removeInvoice();
    }
}

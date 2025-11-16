package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentSessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentSessionsTrackerImpl implements AppointmentSessionTracker {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentSessionsTrackerImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void track() {

    }
}

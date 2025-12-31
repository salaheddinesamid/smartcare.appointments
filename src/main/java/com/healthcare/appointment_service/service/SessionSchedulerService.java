package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.model.AppointmentSession;

public interface SessionSchedulerService {

    void scheduleSessionCheck(AppointmentSession appointmentSession);
}

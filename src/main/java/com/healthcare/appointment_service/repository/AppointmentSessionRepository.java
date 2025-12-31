package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.AppointmentSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentSessionRepository extends JpaRepository<AppointmentSession, Long> {
}

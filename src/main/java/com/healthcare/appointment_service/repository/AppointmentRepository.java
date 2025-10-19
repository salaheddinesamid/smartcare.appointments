package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    Optional<List<Appointment>> findAllByDoctorIdAndStartDate(Integer doctorId, LocalDateTime dateTime);
    Optional<List<Appointment>> findAllByDoctorId(Integer doctorId);
}

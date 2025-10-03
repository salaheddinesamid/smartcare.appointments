package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Integer> {
}

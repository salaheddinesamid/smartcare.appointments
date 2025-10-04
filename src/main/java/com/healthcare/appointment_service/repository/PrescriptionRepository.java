package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription,Integer> {

    Optional<Prescription> findByPrescriptionReferenceNumber(String ref);
}

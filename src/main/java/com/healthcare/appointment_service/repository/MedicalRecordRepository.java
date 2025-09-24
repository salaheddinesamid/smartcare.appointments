package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
}

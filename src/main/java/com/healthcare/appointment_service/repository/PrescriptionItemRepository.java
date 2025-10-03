package com.healthcare.appointment_service.repository;

import com.healthcare.appointment_service.model.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionDetail,Integer> {
}

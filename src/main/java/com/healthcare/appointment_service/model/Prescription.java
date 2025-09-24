package com.healthcare.appointment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prescriptionId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus prescriptionStatus;

    @Transient
    private List<Integer> medicines;
}

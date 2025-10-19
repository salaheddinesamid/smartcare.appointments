package com.healthcare.appointment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prescriptionId;

    @Column(name = "prescription_reference_no", unique = true, nullable = false)
    private String prescriptionReferenceNumber;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PrescriptionStatus prescriptionStatus;

    @OneToMany
    @JoinColumn(name = "prescription_detail_id")
    private List<PrescriptionDetail> details;

    @PrePersist
    private void generateReferenceNumber(){
        this.prescriptionReferenceNumber = UUID.randomUUID().toString();
    }
}

package com.healthcare.appointment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "prescription_detail")
@Entity
@Getter
@Setter
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer prescriptionId;

    @Transient
    private Integer medicineId;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "route")
    @Enumerated(EnumType.STRING)
    private MedicineRoute route;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "instructions")
    private String instructions;
}

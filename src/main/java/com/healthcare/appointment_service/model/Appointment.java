package com.healthcare.appointment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "disease")
    @Enumerated(EnumType.STRING)
    private Disease disease;

    @Column(name = "start_date_time")
    private LocalDateTime startDate;

    @Column(name = "end_date_time")
    private LocalDateTime endDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}

package com.healthcare.appointment_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "appointment_session")
public class AppointmentSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @OneToOne
    private Appointment appointment;

    @Column(name = "duration")
    private long duration;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "time_left")
    private long timeLeft;

    @Column(name = "status")
    private AppointmentSessionStatus status;
}

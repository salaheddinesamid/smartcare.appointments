package com.healthcare.appointment_service.unit;

import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentSession;
import com.healthcare.appointment_service.model.AppointmentSessionStatus;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.repository.AppointmentSessionRepository;
import com.healthcare.appointment_service.service.implementation.SessionSchedulerServiceImpl;
import com.healthcare.appointment_service.service.implementation.WebSocketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;

public class AppointmentSessionTrackerUnitTest {

    @Mock
    private ScheduledExecutorService scheduledExecutorService;

    @Mock
    private AppointmentSessionRepository appointmentSessionRepository;
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private WebSocketServiceImpl webSocketService;

    @InjectMocks
    private SessionSchedulerServiceImpl sessionSchedulerService;

    private Appointment appointment;
    private AppointmentSession appointmentSession;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        // Mock the appointment
        appointment.setAppointmentId(1);
        appointment.setPatientId(2);
        appointment.setStartDate(LocalDateTime.now());
        appointment.setEndDate(LocalDateTime.now().plusMinutes(60));

        // Mock the session
        appointmentSession.setAppointment(appointment);
        appointmentSession.setStatus(AppointmentSessionStatus.ONGOING);
        appointmentSession.setTimeLeft();
    }

    @Test
    void testTrackSessionSuccess(){}

    @Test
    void testTrackSessionAlreadyOnGoingFailed(){}
}

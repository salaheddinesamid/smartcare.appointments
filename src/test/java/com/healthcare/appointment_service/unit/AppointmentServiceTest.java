package com.healthcare.appointment_service.unit;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentScheduleService;
import com.healthcare.appointment_service.service.implementation.AppointmentRescheduleServiceImpl;
import com.healthcare.appointment_service.service.implementation.AppointmentScheduleServiceImpl;
import com.healthcare.appointment_service.service.implementation.AppointmentStarterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentScheduleServiceImpl appointmentScheduleService;

    @InjectMocks
    private AppointmentStarterServiceImpl appointmentStarterService;

    @InjectMocks
    private AppointmentRescheduleServiceImpl appointmentRescheduleService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testScheduleAppointment(){

        // Mock appointment request:
        AppointmentRequestDTO request = new AppointmentRequestDTO(
                1,
                "",
                1,
                "CONSULTATION",
                "DIABETES",
                LocalDateTime.now(),
                null
        );

        // Act:
        appointmentScheduleService.scheduleAppointment(request);
        // Verify:
        verify(appointmentRepository, times(1)).save(any());


    }

    @Test
    void scheduleAppointmentShouldThrow(){}

    @Test
    void rescheduleAppointmentSuccessfully(){}

    @Test
    void rescheduleAppointmentShouldThrow(){}

    @Test
    void testStartAppointmentSession(){}

    @Test
    void startAppointmentSessionShouldThrow(){}

    @Test
    void testEndAppointmentSession(){}

    @Test
    void endAppointmentSessionShouldThrow(){}
}

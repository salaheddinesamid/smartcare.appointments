package com.healthcare.appointment_service.unit;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class AppointmentServiceTest {
    @Mock
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * This unit test used to
     */
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

        NewAppointmentResponseDTO response = appointmentService
                .scheduleAppointment(request);





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

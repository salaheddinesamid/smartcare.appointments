package com.healthcare.appointment_service.unit;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.implementation.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        // Patient
        Integer patientId = 1;
        String patientNationalId = "";
        // Doctor
        Integer doctorId = 2;

        LocalDateTime startDate = LocalDateTime.now();

        AppointmentRequestDTO request = new AppointmentRequestDTO(
                patientId,
                patientNationalId,
                doctorId,
                "CONSULTATION",
                "DIABETES",
                startDate
        );



    }
}

package com.healthcare.appointment_service.unit;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.DoctorDto;
import com.healthcare.appointment_service.dto.PatientDto;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.implementation.AppointmentScheduleServiceImpl;
import com.healthcare.appointment_service.service.implementation.PatientServiceClientImpl;
import com.healthcare.appointment_service.service.implementation.StaffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class AppointmentSchedulerUnitTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private StaffServiceImpl staffService;
    @Mock
    private  PatientServiceClientImpl patientServiceClient;
    @InjectMocks
    private AppointmentScheduleServiceImpl appointmentScheduleService;

    // Mock the doctors
    private PatientDto patient;
    private DoctorDto doctor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        patient = new PatientDto(
                1,
                "Salaheddine",
                "Samid",
                "samidsalaheddine2@gmail.com",
                "",
                10,
                "T315730",
                "",
                ""
        );

        doctor = new DoctorDto();
    }

    @Test
    void testScheduleAppointmentSuccess(){
        // Mock request dto:
        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO(
                1,
                "T315730",
                2,
                "CONSULTATION",
                "DIABETES",
                LocalDateTime.now(),
                null
        );
        // Arrange:
        when(patientServiceClient.checkPatientExistence(appointmentRequestDTO.getPatientNationalId())).thenReturn(true);
        when(staffService.checkDoctorExistence(appointmentRequestDTO.getDoctorId())).thenReturn(true);
        when(staffService.checkDoctorAvailability(appointmentRequestDTO.getDoctorId(), appointmentRequestDTO.getStartDate())).thenReturn(true);


        // Act and verify:
        appointmentScheduleService.scheduleAppointment(appointmentRequestDTO);
        verify(appointmentRepository, times(1)).save(any());
    }

    @Test
    void testScheduleAppointmentThrowsDoctorNotFound(){}

    @Test
    void testScheduleAppointmentThrowsPatientNotFound(){}
}

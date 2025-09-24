package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.exception.AppointmentCannotBeScheduledException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentType;
import com.healthcare.appointment_service.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Value("${application.staff-service}")
    private static String STAFF_MANAGEMENT_URI;
    private final RestTemplate restTemplate;

    @Autowired
    public AppointmentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<?> scheduleAppointment(AppointmentRequestDTO appointmentRequestDTO) {

        // First we check if the patient & the doctor already exist in the system
        boolean patientExistence = checkPatientExistence();
        boolean doctorExistence = checkDoctorExistence(appointmentRequestDTO.getDoctorId());

        if(!patientExistence && !doctorExistence){
            throw new AppointmentCannotBeScheduledException();
        }

        // Create new appointment:
        Appointment appointment = new Appointment();
        appointment.setAppointmentType(AppointmentType.valueOf(appointmentRequestDTO.getAppointmentType()));
        appointment.setDisease(appointmentRequestDTO.getDisease());
        appointment.setStartDate(appointmentRequestDTO.getStartDate());

        // generate invoice:

        return ResponseEntity
                .status(200)
                .body(Map.of("message","Appointment has been scheduled successfully"));

    }

    /**
     * This function is used to check the existence of the patient from patient-management service
     * @return boolean
     */
    private boolean checkPatientExistence(){
        return false;
    }

    /**
     *
     * @return
     */
    private boolean checkDoctorExistence(Integer doctorId){
        String uri = STAFF_MANAGEMENT_URI + "api/staff-management/doctor/check-existence?doctorId="+doctorId;
        ResponseEntity<Boolean> response = restTemplate
                .getForEntity(uri,Boolean.class);

        response.getBody();
        return response.getBody();

    }

    /**
     * This method is used to automatically generate appointment invoice and save it to billing-service
     * @return
     */
    private ResponseEntity<?> generateInvoice(){
        return ResponseEntity.status(200).body("");
    }

    @Override
    public ResponseEntity<?> rescheduleAppointment(Integer appointmentId, LocalDateTime newDate) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllPatientAppointments(Integer patientId) {
        return null;
    }

    @Override
    public List<AppointmentResponseDto> getAllDoctorAppointments() {
        return List.of();
    }

    @Override
    public AppointmentResponseDto scheduleNextAppointment(Integer doctorId, Integer patientId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createPrescription() {
        return null;
    }
}

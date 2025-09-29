package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.ApiResponse;
import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.exception.AppointmentCannotBeScheduledException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentStatus;
import com.healthcare.appointment_service.model.AppointmentType;
import com.healthcare.appointment_service.model.Disease;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {


    private static final String STAFF_MANAGEMENT_URI = "http://localhost:9000";
    private static final String PATIENT_MANAGEMENT_URI = "http://localhost:8091";
    private final RestTemplate restTemplate;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(RestTemplate restTemplate, AppointmentRepository appointmentRepository) {
        this.restTemplate = restTemplate;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public NewAppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO appointmentRequestDTO) {

        // First we check if the patient & the doctor already exist in the system
        boolean patientExistence = checkPatientExistence(appointmentRequestDTO.getPatientNationalId());
        boolean doctorExistence = checkDoctorExistence(appointmentRequestDTO.getDoctorId());

        if(!patientExistence || !doctorExistence){
            log.warn("The appointment cannot be scheduled. The status existence of patient is:{}",patientExistence);
            log.warn("The appointment cannot be scheduled. The status existence of doctor is:{}",doctorExistence);
            throw new AppointmentCannotBeScheduledException();
        }

        // Create new appointment:
        Appointment appointment = new Appointment();
        appointment.setDoctorId(appointmentRequestDTO.getDoctorId());
        appointment.setAppointmentType(AppointmentType.valueOf(appointmentRequestDTO.getAppointmentType()));
        appointment.setDisease(Disease.valueOf(appointmentRequestDTO.getDisease()));
        appointment.setStartDate(appointmentRequestDTO.getStartDate());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment =  appointmentRepository.save(appointment);

        return new NewAppointmentResponseDTO(savedAppointment);

    }

    /**
     * This function is used to check the existence of the patient from patient-management service
     * @return boolean
     */
    private boolean checkPatientExistence(String nationalId){

        String uri = PATIENT_MANAGEMENT_URI + "/api/patient-management/verify-existence?nationalId="+ nationalId;

        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ApiResponse<Boolean>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<Boolean>>() {
                        }
                );

        return response.getBody().getData();
    }

    /**
     *
     * @return
     */
    private boolean checkDoctorExistence(Integer doctorId){
        String uri = STAFF_MANAGEMENT_URI + "/api/staff-management/doctor/check-existence?doctorId="+doctorId;
        ResponseEntity<Boolean> response = restTemplate
                .exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        Boolean.class
                );

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

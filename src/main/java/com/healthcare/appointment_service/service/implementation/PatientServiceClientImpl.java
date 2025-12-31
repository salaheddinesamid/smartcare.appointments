package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.ApiResponse;
import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.DoctorDto;
import com.healthcare.appointment_service.dto.PatientDto;
import com.healthcare.appointment_service.exception.AppointmentNotFoundException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.PatientServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientServiceClientImpl implements PatientServiceClient {

    @Value("${services.patient-service}")
    private String PATIENT_MANAGEMENT_URI;
    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;
    private final StaffServiceImpl staffService;

    public PatientServiceClientImpl(AppointmentRepository appointmentRepository, RestTemplate restTemplate, StaffServiceImpl staffService) {
        this.appointmentRepository = appointmentRepository;
        this.restTemplate = restTemplate;
        this.staffService = staffService;
    }

    @Override
    public boolean checkPatientExistence(String nationalId) {
        return false;
    }

    @Override
    public List<PatientDto> getPatients(List<Integer> ids) {
        String uri = PATIENT_MANAGEMENT_URI + "/api/patient-management/get-patients";
        HttpEntity<List<Integer>> entity = new HttpEntity<>(ids);

        log.info("Fetching patients from service: {}",uri);
        ResponseEntity<ApiResponse<List<PatientDto>>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {
                        }
                );

        return response.getBody().getData();
    }

    @Override
    public PatientDto getPatient(Integer patientId) {
        String uri = PATIENT_MANAGEMENT_URI + "/api/patient-management/get?patientId="+patientId;

        log.info("Fetching patient from service: {}",uri);
        ResponseEntity<ApiResponse<PatientDto>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );

        return response.getBody().getData();
    }

    @Override
    public List<AppointmentResponseDto> getAllPatientAppointment(Integer patientId) {
        // Fetch all the appointments from the db:
        List<Appointment> appointments =
                appointmentRepository.findAllByPatientId(patientId).orElseThrow(()-> new AppointmentNotFoundException(patientId));

        // Get doctor ids:
        List<Integer> doctorsId = appointments.stream().map(Appointment::getDoctorId).toList();


        // Fetch patients and doctor information:
        List<DoctorDto> doctors = staffService.getDoctors(doctorsId);
        PatientDto patient = getPatient(patientId);


        // Map doctors for quick look up:
        Map<Integer,DoctorDto> doctorsMap =
                doctors.stream().collect(Collectors.toMap(DoctorDto::getDoctorId, d->d));

        return
                appointments.stream()
                        .map(appointment -> {
                            DoctorDto doctor = doctorsMap.get(appointment.getDoctorId());
                            return new AppointmentResponseDto(
                                    appointment,
                                    patient,
                                    doctor
                            );
                        }).toList();
    }
}

package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.ApiResponse;
import com.healthcare.appointment_service.dto.DoctorDto;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Value("${services.staff-service}")
    private String STAFF_MANAGEMENT_URI;

    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public StaffServiceImpl(AppointmentRepository appointmentRepository, RestTemplate restTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean checkDoctorAvailability(Integer doctorId, LocalDateTime date) {
        // Fetch doctor appointments:
        return appointmentRepository.findAllByDoctorIdAndStartDate(doctorId,date)
                .isEmpty();
    }

    @Override
    public boolean checkDoctorExistence(Integer doctorId) {
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

    @Override
    public DoctorDto getDoctor(Integer doctorId) {
        String uri = STAFF_MANAGEMENT_URI + "/api/staff-management/doctor?doctorId="+doctorId;
        ResponseEntity<ApiResponse<DoctorDto>> response =
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
    public List<DoctorDto> getDoctors(List<Integer> ids) {
        String uri = STAFF_MANAGEMENT_URI + "/api/staff-management/doctor/get-doctors";

        HttpEntity<List<Integer>> entity = new HttpEntity<>(ids);
        log.info("Fetching doctors from service: {}",uri);
        ResponseEntity<ApiResponse<List<DoctorDto>>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {
                        }
                );

        return response.getBody().getData();
    }
}

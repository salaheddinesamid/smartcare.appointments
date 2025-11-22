package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.AppointmentCreatedEvent;
import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.exception.AppointmentCannotBeScheduledException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentStatus;
import com.healthcare.appointment_service.model.AppointmentType;
import com.healthcare.appointment_service.model.Disease;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentScheduleServiceImpl implements AppointmentScheduleService {

    private final AppointmentRepository appointmentRepository;
    private final StaffServiceImpl staffService;
    private final PatientServiceClientImpl patientServiceClient;
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public AppointmentScheduleServiceImpl(AppointmentRepository appointmentRepository, StaffServiceImpl staffService, PatientServiceClientImpl patientServiceClient, KafkaTemplate kafkaTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.staffService = staffService;
        this.patientServiceClient = patientServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public NewAppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO requestDTO) {

        // First we check if the patient & the doctor already exist in the system
        boolean patientExistence = patientServiceClient.checkPatientExistence(requestDTO.getPatientNationalId());
        boolean doctorExistence = staffService.checkDoctorExistence(requestDTO.getDoctorId());

        if(!patientExistence || !doctorExistence){
            log.warn("The appointment cannot be scheduled. The status existence of patient is:{}",patientExistence);
            log.warn("The appointment cannot be scheduled. The status existence of doctor is:{}",doctorExistence);
            throw new AppointmentCannotBeScheduledException();
        }

        // Check doctor availability:
        if(!staffService.checkDoctorAvailability(requestDTO.getDoctorId(), requestDTO.getStartDate())){
            throw new AppointmentCannotBeScheduledException();
        }

        // Create new appointment:
        Appointment appointment = new Appointment();
        appointment.setDoctorId(requestDTO.getDoctorId());
        appointment.setAppointmentType(AppointmentType.valueOf(requestDTO.getAppointmentType()));
        appointment.setDisease(Disease.valueOf(requestDTO.getDisease()));
        appointment.setStartDate(requestDTO.getStartDate());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setPatientId(requestDTO.getPatientId());

        Appointment savedAppointment =  appointmentRepository.save(appointment);

        // create event, and send it to Kafka consumer (Invoice Service):
        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                savedAppointment
        );
        // send event to Kafka topic in case of new appointment scheduled
        kafkaTemplate.send("appointment-created",event);

        // return response:
        return new NewAppointmentResponseDTO(savedAppointment);
    }
}

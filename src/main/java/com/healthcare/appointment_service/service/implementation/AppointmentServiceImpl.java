package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.*;
import com.healthcare.appointment_service.exception.*;
import com.healthcare.appointment_service.model.*;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.repository.PrescriptionItemRepository;
import com.healthcare.appointment_service.repository.PrescriptionRepository;
import com.healthcare.appointment_service.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final String STAFF_MANAGEMENT_URI = "http://localhost:9020";
    private static final String PATIENT_MANAGEMENT_URI = "http://localhost:8091";
    private final RestTemplate restTemplate;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    public AppointmentServiceImpl(RestTemplate restTemplate, AppointmentRepository appointmentRepository, PrescriptionRepository prescriptionRepository, PrescriptionItemRepository prescriptionItemRepository) {
        this.restTemplate = restTemplate;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionItemRepository = prescriptionItemRepository;
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
        appointment.setPatientId(appointmentRequestDTO.getPatientId());

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
    public NewAppointmentResponseDTO rescheduleAppointment(Integer appointmentId, LocalDateTime newDate) {
        // Fetch the appointment
        Appointment appointment = appointmentRepository
                .findById(appointmentId).orElseThrow(()-> new AppointmentNotFoundException(appointmentId));

        // Check if the doctor is available
        boolean isDoctorAvailable = checkDoctorAvailability(appointment.getDoctorId(),newDate);
        if(!isDoctorAvailable){
            throw new UnavailableDoctorException("");
        }

        appointment.setStartDate(newDate);

        // save the appointment
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new NewAppointmentResponseDTO(
                savedAppointment
        );

    }

    /**
     * This method is used to check the doctor availability
     * @param date
     * @return if there is an appointment in that date or not
     */
    private boolean checkDoctorAvailability(Integer id,LocalDateTime date){
        // Fetch doctor appointments:
        return appointmentRepository.findAllByDoctorIdAndStartDate(id,date)
                .isEmpty();
    }

    @Override
    public List<AppointmentResponseDto> getAllPatientAppointments(Integer patientId) {
        // Fetch all the appointments from the db:
        List<Appointment> appointments =
                appointmentRepository.findAllByPatientId(patientId).orElseThrow(()-> new AppointmentNotFoundException(patientId));

        // Get doctor ids:
        List<Integer> doctorsId = appointments.stream().map(Appointment::getDoctorId).toList();


        // Fetch patients and doctor information:
        List<DoctorDto> doctors = getDoctors(doctorsId);
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

    @Override
    public List<AppointmentResponseDto> getPatientAppointmentsByDate(Integer patientId, LocalDate date) {
        return List.of();
    }



    @Override
    public List<AppointmentResponseDto> getAllDoctorAppointments(Integer doctorId) {
        // Fetch all the appointments from the db:
        List<Appointment> appointments =
                appointmentRepository.findAllByDoctorId(doctorId).orElseThrow(()-> new AppointmentNotFoundException(doctorId));

        // Get patient ids:
        List<Integer> patientIds = appointments.stream().map(Appointment::getPatientId).toList();


        // Fetch patients and doctor information:
        List<PatientDto> patients = getPatients(patientIds);
        DoctorDto doctor = getDoctor(doctorId);


        // Map patients for quick look up:
        Map<Integer,PatientDto> patientsMap =
                patients.stream().collect(Collectors.toMap(PatientDto::getPatientId, p->p));

        return
                appointments.stream()
                        .map(appointment -> {
                            PatientDto patient = patientsMap.get(appointment.getPatientId());
                            return new AppointmentResponseDto(
                                    appointment,
                                    patient,
                                    doctor
                            );
                        }).toList();

    }

    public List<AppointmentResponseDto> getDoctorAppointmentsByDate(Integer doctorId, LocalDate date) {

        LocalDateTime localDateTime = date.atStartOfDay();
        List<Appointment> appointments =
                appointmentRepository.findAllByDoctorIdAndStartDate(doctorId,localDateTime).orElseThrow();
        // Get patient ids:
        List<Integer> patientIds = appointments.stream().map(Appointment::getPatientId).toList();


        // Fetch patients and doctor information:
        List<PatientDto> patients = getPatients(patientIds);
        DoctorDto doctor = getDoctor(doctorId);


        // Map patients for quick look up:
        Map<Integer,PatientDto> patientsMap =
                patients.stream().collect(Collectors.toMap(PatientDto::getPatientId, p->p));

        return
                appointments.stream()
                        .map(appointment -> {
                            PatientDto patient = patientsMap.get(appointment.getPatientId());
                            return new AppointmentResponseDto(
                                    appointment,
                                    patient,
                                    doctor
                            );
                        }).toList();
    }

    private DoctorDto getDoctor(Integer doctorId){
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
    public AppointmentResponseDto scheduleNextAppointment(Integer doctorId, Integer patientId) {
        return null;
    }

    @Override
    public NewPrescriptionResponseDto createPrescription(
            Integer appointmentId,
            NewPrescriptionDto newPrescriptionDto) {

        // Fetch the appointment:
        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        // Create new prescription
        Prescription prescription = new Prescription();

        if(!newPrescriptionDto.getDetails().isEmpty()){
            List<PrescriptionDetail> details =  newPrescriptionDto.getDetails()
                    .stream().map(prescriptionDetail -> {
                        PrescriptionDetail item = new PrescriptionDetail();
                        item.setDosage(prescriptionDetail.getDosage());
                        item.setFrequency(prescriptionDetail.getFrequency());
                        item.setRoute(MedicineRoute.valueOf(prescriptionDetail.getRoute()));
                        item.setDuration(prescriptionDetail.getDuration());
                        item.setInstructions(prescriptionDetail.getInstructions());
                        return prescriptionItemRepository.save(item);
                    }).toList();

            prescription.setDetails(details);
        }
        prescription.setIssueDate(LocalDateTime.now());
        prescription.setValidUntil(newPrescriptionDto.getValidUntil());
        prescription.setDoctorId(appointment.getDoctorId());
        prescription.setPatientId(appointment.getPatientId());
        prescription.setAppointment(appointment);

        // Save the prescription
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        appointment.setPrescription(savedPrescription);

        // Return a object response
        return new NewPrescriptionResponseDto(
                prescription
        );
    }

    @Override
    public List<AppointmentResponseDto> getAll() {
        // Fetch all appointments
        List<Appointment> appointments = appointmentRepository.findAll();

        // Get patients id
        List<Integer> patientsId =
                appointments.stream().map(Appointment::getPatientId)
                        .toList();

        // Get doctors id
        List<Integer> doctorsId =
                appointments.stream()
                        .map(Appointment::getDoctorId)
                        .toList();

        // FETCH PATIENTS AND DOCTORS
        List<PatientDto> patients = getPatients(patientsId);
        List<DoctorDto> doctors = getDoctors(doctorsId);

        // Map for quick look up
        Map<Integer,PatientDto> patientsMap = patients
                .stream().collect(Collectors.toMap(PatientDto::getPatientId, p-> p));

        Map<Integer,DoctorDto> doctorsMap = doctors
                .stream().collect(Collectors.toMap(DoctorDto::getDoctorId, d-> d));

        return
                appointments.stream()
                        .map(appointment -> {
                            PatientDto patient = patientsMap.get(appointment.getPatientId());
                            DoctorDto doctor = doctorsMap.get(appointment.getDoctorId());
                            return new AppointmentResponseDto(
                                    appointment,
                                    patient,
                                    doctor
                            );
                        }).toList();
    }

    @Override
    public AppointmentSessionDto startAppointmentSession(Integer appointmentId) {
        long duration = 60;
        Appointment appointment =
                appointmentRepository.findById(appointmentId).orElseThrow();

        // Check if the appointment has not been started & and it's the right date:
        boolean notStarted = appointment.getStatus().equals(AppointmentStatus.SCHEDULED);
        boolean validStartDate = appointment.getStartDate().isBefore(LocalDateTime.now());

        // Check if the appointment already started
        if(!notStarted){
            throw new AppointmentAlreadyStartedException(appointmentId);
        }

        // Check if the start date is arrived
        if(!validStartDate){
            throw new AppointmentStartDateNotValidException();
        }

        appointment.setStatus(AppointmentStatus.ON_GOING);
        appointment.setEndDate(LocalDateTime.now().plusMinutes(duration));
        Appointment savedAppointment =  appointmentRepository.save(appointment);

        return new AppointmentSessionDto(
                savedAppointment
        );

    }

    @Override
    public AppointmentSessionDto completeAppointmentSession(Integer appointmentId) {
        // Fetch the appointment from db:

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        // Throw runtime exception if the appointment is already completed
        if(appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
            throw new AppointmentAlreadyCompletedException();
        }

        // Throw runtime exception if the appointment is already canceled
        if(appointment.getStatus().equals(AppointmentStatus.CANCELED)){
            throw new AppointmentAlreadyCanceled();
        }

        // Otherwise
        // update the appointment:
        appointment.setEndDate(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.COMPLETED);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentSessionDto(
                savedAppointment
        );
    }

    @Override
    public PrescriptionValidityResponseDto checkPrescriptionValidity(String refNumber) {

        Prescription prescription = prescriptionRepository
                .findByPrescriptionReferenceNumber(refNumber)
                .orElseThrow();

        PrescriptionResponseDto details = new PrescriptionResponseDto(
                prescription
        );

        return new PrescriptionValidityResponseDto(
                true,
                details
        );
    }

    private List<PatientDto> getPatients(List<Integer> ids){
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

    private PatientDto getPatient(Integer patientId){
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

    private List<DoctorDto> getDoctors(List<Integer> ids){
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

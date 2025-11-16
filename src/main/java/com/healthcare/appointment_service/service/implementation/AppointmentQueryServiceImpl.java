package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.AppointmentResponseDto;
import com.healthcare.appointment_service.dto.DoctorDto;
import com.healthcare.appointment_service.dto.PatientDto;
import com.healthcare.appointment_service.exception.AppointmentNotFoundException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.service.AppointmentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;
    private final StaffServiceImpl staffService;
    private final PatientServiceClientImpl patientServiceClient;

    @Autowired
    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository, StaffServiceImpl staffService, PatientServiceClientImpl patientServiceClient) {
        this.appointmentRepository = appointmentRepository;
        this.staffService = staffService;
        this.patientServiceClient = patientServiceClient;
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
        List<PatientDto> patients = patientServiceClient.getPatients(patientsId);
        List<DoctorDto> doctors = staffService.getDoctors(doctorsId);

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
    public List<AppointmentResponseDto> getAllDoctorAppointments(Integer doctorId) {
        return List.of();
    }
}

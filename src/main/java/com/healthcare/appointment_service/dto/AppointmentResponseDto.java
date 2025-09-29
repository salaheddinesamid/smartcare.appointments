package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponseDto {

    private Integer appointmentId;
    private String disease;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String type;
    private String status;
    private PatientInformation patientInformation;
    private DoctorInformation doctorInformation;

    public AppointmentResponseDto(
            Appointment appointment,
            PatientDto patient,
            DoctorDto doctor
    ){
        this.appointmentId = appointment.getAppointmentId();
        this.disease = appointment.getDisease().toString();
        this.startDate = appointment.getStartDate();
        this.endDate = appointment.getEndDate();
        this.type = appointment.getAppointmentType().toString();
        this.status = appointment.getStatus().toString();
        this.patientInformation = new PatientInformation(patient);
        this.doctorInformation = new DoctorInformation(doctor);
    }
}

@Data
class PatientInformation{

    private String firstName;
    private String lastName;
    private String email;
    private Integer patientId;
    private String nationalId;
    private String address;
    private String status;

    public PatientInformation(PatientDto patientDto){
        this.firstName = patientDto.getFirstName();
        this.lastName = patientDto.getLastName();
        this.email = patientDto.getEmail();
        this.patientId = patientDto.getPatientId();
        this.nationalId = patientDto.getNationalId();
        this.address = patientDto.getAddress();
        this.status = patientDto.getStatus();
    }

}

@Data
class DoctorInformation{

    private String firstName;
    private String lastName;
    private String email;

    private String professionalId;
    private String speciality;
    private Integer yearsOfExperience;

    public DoctorInformation(DoctorDto doctorDto){
        this.firstName = doctorDto.getFirstName();
        this.lastName = doctorDto.getLastName();
        this.email = doctorDto.getEmail();
        this.professionalId = doctorDto.getProfessionalId();
        this.speciality = doctorDto.getSpeciality();
        this.yearsOfExperience = doctorDto.getYearsOfExperience();
    }
}

package com.healthcare.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {

    private Integer appointmentId;
    private PatientInformation patientInformation;
    private DoctorInformation doctorInformation;
}
class PatientInformation{

}
class DoctorInformation{

}

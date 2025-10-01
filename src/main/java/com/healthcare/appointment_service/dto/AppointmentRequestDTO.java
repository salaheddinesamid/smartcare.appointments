package com.healthcare.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {

    private Integer patientId;
    private String patientNationalId;
    private Integer doctorId;
    private String appointmentType;
    private String disease;
    private LocalDateTime startDate;
}


@Data
class PrescriptionDetails{

}

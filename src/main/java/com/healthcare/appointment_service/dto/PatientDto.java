package com.healthcare.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private Integer patientId;
    private String nationalId;
    private String address;
    private String status;
}

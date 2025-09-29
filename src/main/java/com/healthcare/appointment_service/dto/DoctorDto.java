package com.healthcare.appointment_service.dto;

import lombok.Data;

@Data
public class DoctorDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String professionalId;
    private String speciality;
    private Integer yearsOfExperience;
}

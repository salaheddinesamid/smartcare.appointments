package com.healthcare.appointment_service.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}

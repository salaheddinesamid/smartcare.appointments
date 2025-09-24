package com.healthcare.appointment_service.controller;

import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.service.implementation.AppointmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleNewAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return  appointmentService.scheduleAppointment(appointmentRequestDTO);
    }
}

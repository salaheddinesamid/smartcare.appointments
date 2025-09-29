package com.healthcare.appointment_service.controller;

import com.healthcare.appointment_service.dto.ApiResponse;
import com.healthcare.appointment_service.dto.AppointmentRequestDTO;
import com.healthcare.appointment_service.dto.NewAppointmentResponseDTO;
import com.healthcare.appointment_service.service.implementation.AppointmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponse<?>> scheduleNewAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){

        NewAppointmentResponseDTO newAppointmentResponseDTO = appointmentService.scheduleAppointment(appointmentRequestDTO);
        ApiResponse<NewAppointmentResponseDTO> response = new ApiResponse<>(
                true,
                "Appointment scheduled successfully",
                newAppointmentResponseDTO
        );

        return ResponseEntity
                .ok().body(response);
    }

    @PutMapping("reschedule")
    public ResponseEntity<ApiResponse<?>> reschedule(@RequestParam Integer appointmentId, @RequestParam LocalDateTime newDate){

        NewAppointmentResponseDTO updatedAppointment = appointmentService.rescheduleAppointment(appointmentId,newDate);

        ApiResponse<NewAppointmentResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "The appointment has been rescheduled successfully",
                        updatedAppointment
                );
        return ResponseEntity
                .status(200)
                .body(response);

    }
}

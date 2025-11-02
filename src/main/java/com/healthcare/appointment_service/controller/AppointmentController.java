package com.healthcare.appointment_service.controller;

import com.healthcare.appointment_service.dto.*;
import com.healthcare.appointment_service.service.implementation.AppointmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @PutMapping("start-session")
    public ResponseEntity<ApiResponse<?>> startAppointment(@RequestParam Integer appointmentId){

        AppointmentSessionDto session = appointmentService.startAppointmentSession(appointmentId);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "The session has already been started",
                session
        );

        return ResponseEntity
                .status(200)
                .body(response);

    }

    @PutMapping("complete-session")
    ResponseEntity<ApiResponse<?>> completeAppointment(@RequestParam Integer appointmentId){
        AppointmentSessionDto session = appointmentService.completeAppointmentSession(appointmentId);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "",
                session
        );
        return ResponseEntity.status(200)
                .body(response);
    }

    @PutMapping("cancel")
    ResponseEntity<ApiResponse<?>> cancelAppointment(@RequestParam Integer appointmentId){
        ApiResponse<?> response = appointmentService
                .cancelAppointment(appointmentId);
        return ResponseEntity.status(200)
                .body(response);
    }

    @GetMapping("get_all")
    public ResponseEntity<ApiResponse<?>> getAllAppointments(){
        List<AppointmentResponseDto> appointments = appointmentService.getAll();

        ApiResponse<List<AppointmentResponseDto>> response = new ApiResponse<>(
                true,
                "",
                appointments
        );

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("doctor/get_all")
    public ResponseEntity<ApiResponse<?>> getAllDoctorAppointments(@RequestParam Integer doctorId){
        List<AppointmentResponseDto> appointments = appointmentService.getAllDoctorAppointments(doctorId);

        ApiResponse<?> response =
                new ApiResponse<>(
                        true,
                        "",
                        appointments
                );
        return ResponseEntity
                .status(200)
                .body(response);
    }

    @GetMapping("doctor/get-by-date")
    public ResponseEntity<ApiResponse<?>> getDoctorAppointmentByDate(@RequestParam Integer doctorId,@RequestParam LocalDate date){
        List<AppointmentResponseDto> appointments =
                appointmentService.getDoctorAppointmentsByDate(doctorId,date);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "",
                appointments
        );

        return ResponseEntity.status(200)
                .body(response);
    }

    @GetMapping("patient/get_all")
    public ResponseEntity<ApiResponse<?>> getAllPatientAppointments(@RequestParam Integer patientId){
        List<AppointmentResponseDto> appointments = appointmentService
                .getAllPatientAppointments(patientId);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "",
                appointments
        );

        return ResponseEntity
                .status(200)
                .body(response);
    }

    @GetMapping("patient/get-by-date")
    public ResponseEntity<ApiResponse<?>> getAllPatientAppointmentsByDate(@RequestParam LocalDateTime date){
        return null;
    }
}

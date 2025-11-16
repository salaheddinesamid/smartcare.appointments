package com.healthcare.appointment_service.controller;

import com.healthcare.appointment_service.dto.*;
import com.healthcare.appointment_service.service.AppointmentRescheduleService;
import com.healthcare.appointment_service.service.implementation.AppointmentCancelerImpl;
import com.healthcare.appointment_service.service.implementation.AppointmentQueryServiceImpl;
import com.healthcare.appointment_service.service.implementation.AppointmentScheduleServiceImpl;
import com.healthcare.appointment_service.service.implementation.AppointmentStarterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentScheduleServiceImpl appointmentScheduleService;
    private final AppointmentRescheduleService appointmentRescheduleService;
    private final AppointmentStarterServiceImpl appointmentStarterService;
    private final AppointmentCancelerImpl appointmentCanceler;
    private final AppointmentQueryServiceImpl appointmentQueryService;

    @Autowired
    public AppointmentController(AppointmentScheduleServiceImpl appointmentScheduleService, AppointmentRescheduleService appointmentRescheduleService, AppointmentStarterServiceImpl appointmentStarterService, AppointmentCancelerImpl appointmentCanceler, AppointmentQueryServiceImpl appointmentQueryService) {
        this.appointmentScheduleService = appointmentScheduleService;
        this.appointmentRescheduleService = appointmentRescheduleService;
        this.appointmentStarterService = appointmentStarterService;
        this.appointmentCanceler = appointmentCanceler;
        this.appointmentQueryService = appointmentQueryService;
    }


    @PostMapping("/schedule")
    public ResponseEntity<ApiResponse<?>> scheduleNewAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){

        NewAppointmentResponseDTO newAppointmentResponseDTO = appointmentScheduleService.scheduleAppointment(appointmentRequestDTO);
        ApiResponse<NewAppointmentResponseDTO> response = new ApiResponse<>(
                true,
                "Appointment scheduled successfully",
                newAppointmentResponseDTO
        );

        return ResponseEntity
                .ok().body(response);
    }

    // Reschedule Service:
    @PutMapping("reschedule")
    public ResponseEntity<ApiResponse<?>> reschedule(@RequestParam Integer appointmentId, @RequestParam LocalDateTime newDate){

        NewAppointmentResponseDTO updatedAppointment = appointmentRescheduleService.rescheduleAppointment(appointmentId,newDate);

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

        AppointmentSessionDto session = appointmentStarterService.startSession(appointmentId);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "The session has already been started",
                session
        );

        return ResponseEntity
                .status(200)
                .body(response);

    }

    /**
     *
     * @param appointmentId
     * @return
     */
    @PutMapping("complete-session")
    ResponseEntity<ApiResponse<?>> completeAppointment(@RequestParam Integer appointmentId){
        AppointmentSessionDto session = appointmentStarterService.completeSession(appointmentId);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "",
                session
        );
        return ResponseEntity.status(200)
                .body(response);
    }

    /**
     *
     * @param appointmentId
     * @return
     */
    @PutMapping("cancel")
    ResponseEntity<?> cancelAppointment(@RequestParam Integer appointmentId){
        appointmentCanceler
                .cancelAppointment(appointmentId);
        return ResponseEntity.status(200)
                .body(Map.of("message","The appointment has been canceled successfully"));
    }

    @GetMapping("get_all")
    public ResponseEntity<ApiResponse<?>> getAllAppointments(){
        List<AppointmentResponseDto> appointments = appointmentQueryService.getAll();

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
        List<AppointmentResponseDto> appointments = appointmentQueryService.getAllDoctorAppointments(doctorId);

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

    /*
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

     */

    /*
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

     */

    @GetMapping("patient/get-by-date")
    public ResponseEntity<ApiResponse<?>> getAllPatientAppointmentsByDate(@RequestParam LocalDateTime date){
        return null;
    }
}

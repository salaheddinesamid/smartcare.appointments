package com.healthcare.appointment_service.exception;

import com.healthcare.appointment_service.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentExceptionController {

    @ExceptionHandler(AppointmentCannotBeScheduledException.class)
    public ResponseEntity<?> appointmentException(){
        return ResponseEntity.status(405)
                .body("");
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> appointmentNotFound(AppointmentNotFoundException e){
        return
                ResponseEntity.status(404)
                        .body(new ApiResponse<>(
                                false,
                                e.getMessage(),
                                null
                        ));
    }

    @ExceptionHandler(UnavailableDoctorException.class)
    public ResponseEntity<ApiResponse<?>> handleUnavailableDoctor(UnavailableDoctorException ex){
        return ResponseEntity.status(409)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));

    }

    @ExceptionHandler(AppointmentAlreadyStartedException.class)
    public ResponseEntity<ApiResponse<?>> handleAppointmentAlreadyStarted(){
        return
                ResponseEntity.status(500)
                        .body(new ApiResponse<>(
                                false,
                                "",
                                null
                        ));
    }

    @ExceptionHandler(AppointmentAlreadyCompletedException.class)
    public ResponseEntity<ApiResponse<?>> handleAppointmentAlreadyCompleted(){
        return
                ResponseEntity
                        .status(409)
                        .body(new ApiResponse<>(
                                false,
                                "The appointment cannot be completed",
                                null
                        ));
    }

    @ExceptionHandler(AppointmentAlreadyCanceled.class)
    public ResponseEntity<ApiResponse<?>> handleCanceledAppointment(){
        return
                ResponseEntity.status(409)
                        .body(new ApiResponse<>(
                                false,
                                "",
                                null
                        ));
    }
}

package com.healthcare.appointment_service.exception;

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
}

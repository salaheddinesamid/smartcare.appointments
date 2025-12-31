package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.AppointmentSessionDto;
import com.healthcare.appointment_service.exception.AppointmentAlreadyCanceled;
import com.healthcare.appointment_service.exception.AppointmentAlreadyCompletedException;
import com.healthcare.appointment_service.exception.AppointmentAlreadyStartedException;
import com.healthcare.appointment_service.exception.AppointmentStartDateNotValidException;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentSession;
import com.healthcare.appointment_service.model.AppointmentStatus;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.repository.AppointmentSessionRepository;
import com.healthcare.appointment_service.service.AppointmentSessionStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentStarterServiceImpl implements AppointmentSessionStarterService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentSessionRepository appointmentSessionRepository;
    private final SessionSchedulerServiceImpl sessionSchedulerService;

    @Autowired
    public AppointmentStarterServiceImpl(AppointmentRepository appointmentRepository, AppointmentSessionRepository appointmentSessionRepository, SessionSchedulerServiceImpl sessionSchedulerService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentSessionRepository = appointmentSessionRepository;
        this.sessionSchedulerService = sessionSchedulerService;
    }

    @Override
    public AppointmentSessionDto startSession(Integer appointmentId) {
        Appointment appointment =
                appointmentRepository.findById(appointmentId).orElseThrow();

        // Check if the appointment has not been started & and it's the right date:
        boolean validStartDate = appointment.getStartDate().isBefore(LocalDateTime.now());

        if(appointment.getStatus().equals(AppointmentStatus.ON_GOING)){
            throw new AppointmentAlreadyStartedException(appointmentId);
        }

        if(appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
            throw new AppointmentAlreadyCompletedException();
        }

        // Check if the start date is arrived
        if(!validStartDate){
            throw new AppointmentStartDateNotValidException();
        }

        // Create new appointment session:
        AppointmentSession appointmentSession = new AppointmentSession();
        appointmentSession.setAppointment(appointment);
        appointmentSession.setDuration(appointment.getDuration());
        appointmentSession.setTimeLeft(appointment.getDuration());
        appointmentSession.setStartTime(LocalDateTime.now()); // set the start time:
        appointmentSession.setEndTime(LocalDateTime.now().plusMinutes(60)); // set the end time to a default 1 hours


        appointment.setStatus(AppointmentStatus.ON_GOING);
        appointment.setEndDate(appointmentSession.getEndTime());
        Appointment savedAppointment =  appointmentRepository.save(appointment);

        // save the appointment session:
        appointmentSessionRepository.save(appointmentSession);

        // save the appointment:
        appointmentRepository.save(appointment);

        // keep tracking the appointment session:
        sessionSchedulerService.scheduleSessionCheck(appointmentSession);

        return new AppointmentSessionDto(
                savedAppointment
        );
    }

    @Override
    public AppointmentSessionDto completeSession(Integer appointmentId) {
        // Fetch the appointment from db:

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        // Throw runtime exception if the appointment is already completed
        if(appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
            throw new AppointmentAlreadyCompletedException();
        }

        // Throw runtime exception if the appointment is already canceled
        if(appointment.getStatus().equals(AppointmentStatus.CANCELED)){
            throw new AppointmentAlreadyCanceled();
        }

        // Otherwise
        // update the appointment:
        appointment.setEndDate(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.COMPLETED);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentSessionDto(
                savedAppointment
        );
    }
}

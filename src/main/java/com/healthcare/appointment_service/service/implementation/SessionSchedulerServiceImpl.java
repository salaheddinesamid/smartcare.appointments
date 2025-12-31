package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.AppointmentSession;
import com.healthcare.appointment_service.model.AppointmentSessionStatus;
import com.healthcare.appointment_service.model.AppointmentStatus;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.repository.AppointmentSessionRepository;
import com.healthcare.appointment_service.service.SessionSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SessionSchedulerServiceImpl implements SessionSchedulerService {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final AppointmentSessionRepository appointmentSessionRepository;
    private final AppointmentRepository appointmentRepository;
    private final WebSocketServiceImpl webSocketService;

    @Autowired
    public SessionSchedulerServiceImpl(AppointmentSessionRepository appointmentSessionRepository, AppointmentRepository appointmentRepository, WebSocketServiceImpl webSocketService) {
        this.appointmentSessionRepository = appointmentSessionRepository;
        this.appointmentRepository = appointmentRepository;
        this.webSocketService = webSocketService;
    }

    @Override
    public void scheduleSessionCheck(AppointmentSession appointmentSession) {
        Runnable checkTask = ()->{
            // fetch the appointment session:
            AppointmentSession session =
                    appointmentSessionRepository.findById(appointmentSession.getSessionId()).orElseThrow();
            // FETCH THE Appointment:
            Appointment appointment =
                    appointmentRepository.findById(appointmentSession.getAppointment().getAppointmentId()).orElseThrow();
            if(!appointment.getStatus().equals(AppointmentStatus.ON_GOING)) return;

            long secondsLeft = Duration.between(LocalDateTime.now(), session.getEndTime()).getSeconds(); // extract the seconds left by subtracting the difference between now and

            // send time update using web socket:
            webSocketService.sendTimeUpdate(session.getSessionId(), secondsLeft);
            session.setTimeLeft(secondsLeft);

            // if the time is over set the session to completed and end session
            if(secondsLeft <= 0){
                session.setStatus(AppointmentSessionStatus.COMPLETED);
                appointment.setStatus(AppointmentStatus.COMPLETED);

                // save the session:
                appointmentSessionRepository.save(session);
                // save the appointment:
                appointmentRepository.save(appointment);

                // send
            }
        };

        // run the task every second:
        executorService.scheduleAtFixedRate(checkTask, 0, 1, TimeUnit.SECONDS);
    }
}

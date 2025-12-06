package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.model.AppointmentSession;
import com.healthcare.appointment_service.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Override
    public void sendTimeUpdate(Long sessionId, long timeLeft) {

        simpMessagingTemplate.convertAndSend(
                "/topic/session/" + sessionId,
                Map.of(
                        "TYPE", "TIME_UPDATE",
                        "timeLeft", timeLeft
                )
        );
    }

    @Override
    public void sendSessionUpdate(AppointmentSession session) {

    }

    @Override
    public void sendSessionComplete(AppointmentSession session) {

    }
}

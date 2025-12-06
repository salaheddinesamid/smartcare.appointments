package com.healthcare.appointment_service.service;

import com.healthcare.appointment_service.model.AppointmentSession;

/**
 * This class send message in real-time to:
 * Update Countdown of an appointment session.
 * Update the status of the session.
 * Complete the session.
 */
public interface WebSocketService {

    /**
     *
     * @param sessionId
     * @param timeLeft
     */
    void sendTimeUpdate(Long sessionId, long timeLeft);

    /**
     *
     * @param session
     */
    void sendSessionUpdate(AppointmentSession session);

    /**
     *
     * @param session
     */
    void sendSessionComplete(AppointmentSession session);
}

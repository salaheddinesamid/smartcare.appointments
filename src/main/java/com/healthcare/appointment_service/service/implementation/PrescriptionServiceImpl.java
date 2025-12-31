package com.healthcare.appointment_service.service.implementation;

import com.healthcare.appointment_service.dto.NewPrescriptionDto;
import com.healthcare.appointment_service.dto.NewPrescriptionResponseDto;
import com.healthcare.appointment_service.dto.PrescriptionResponseDto;
import com.healthcare.appointment_service.dto.PrescriptionValidityResponseDto;
import com.healthcare.appointment_service.model.Appointment;
import com.healthcare.appointment_service.model.MedicineRoute;
import com.healthcare.appointment_service.model.Prescription;
import com.healthcare.appointment_service.model.PrescriptionDetail;
import com.healthcare.appointment_service.repository.AppointmentRepository;
import com.healthcare.appointment_service.repository.PrescriptionItemRepository;
import com.healthcare.appointment_service.repository.PrescriptionRepository;
import com.healthcare.appointment_service.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionItemRepository prescriptionItemRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, PrescriptionItemRepository prescriptionItemRepository, AppointmentRepository appointmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionItemRepository = prescriptionItemRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public NewPrescriptionResponseDto createPrescription(
            Integer appointmentId,
            NewPrescriptionDto newPrescriptionDto) {

        // Fetch the appointment:
        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow();

        // Create new prescription
        Prescription prescription = new Prescription();

        if(!newPrescriptionDto.getDetails().isEmpty()){
            List<PrescriptionDetail> details =  newPrescriptionDto.getDetails()
                    .stream().map(prescriptionDetail -> {
                        PrescriptionDetail item = new PrescriptionDetail();
                        item.setDosage(prescriptionDetail.getDosage());
                        item.setFrequency(prescriptionDetail.getFrequency());
                        item.setRoute(MedicineRoute.valueOf(prescriptionDetail.getRoute()));
                        item.setDuration(prescriptionDetail.getDuration());
                        item.setInstructions(prescriptionDetail.getInstructions());
                        return prescriptionItemRepository.save(item);
                    }).toList();

            prescription.setDetails(details);
        }
        prescription.setIssueDate(LocalDateTime.now());
        prescription.setValidUntil(newPrescriptionDto.getValidUntil());
        prescription.setDoctorId(appointment.getDoctorId());
        prescription.setPatientId(appointment.getPatientId());
        prescription.setAppointment(appointment);

        // Save the prescription
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        appointment.setPrescription(savedPrescription);

        // Return a object response
        return new NewPrescriptionResponseDto(
                prescription
        );
    }

    @Override
    public PrescriptionValidityResponseDto checkPrescriptionValidity(String refNumber) {

        // Fetch the prescription from db:
        Prescription prescription = prescriptionRepository
                .findByPrescriptionReferenceNumber(refNumber)
                .orElseThrow();

        PrescriptionResponseDto details = new PrescriptionResponseDto(
                prescription
        );

        return new PrescriptionValidityResponseDto(
                true,
                details
        );
    }
}

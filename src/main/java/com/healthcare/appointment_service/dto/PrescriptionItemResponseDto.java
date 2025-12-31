package com.healthcare.appointment_service.dto;

import com.healthcare.appointment_service.model.PrescriptionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrescriptionItemResponseDto {
    private Integer medicineId;
    private String dosage;
    private Integer frequency;
    private String route;
    private Integer duration;
    private String instructions;

    public PrescriptionItemResponseDto(
            PrescriptionDetail item
    ){
        this.medicineId = item.getMedicineId();
        this.dosage = item.getDosage();
        this.duration = item.getDuration();
        this.frequency = item.getFrequency();
        this.route = item.getRoute().toString();
        this.instructions = item.getInstructions();
    }
}

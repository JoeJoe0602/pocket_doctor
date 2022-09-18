package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class AppointmentDTO extends BaseDTO {

    private  String doctorId;

    private  String userId;

    private  String appointmentDate;

    private  String appointmentTime;

    private  String description;
}

package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import com.jolin.domain.Doctor;
import com.jolin.domain.UserInfo;
import lombok.Data;

@Data
public class AppointmentDTO extends BaseDTO {

    private  UserInfoDTO user;
    private UserInfoDTO doctor;

    private  String doctorId;

    private  String userId;

    private  String appointmentDate;

    private  String appointmentTime;

    private  String description;
}

package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Appointment  extends BaseDomain {

    private  String doctorId;

    private  String userId;

    private  String appointmentDate;

    private  String appointmentTime;

    private  String description;

}

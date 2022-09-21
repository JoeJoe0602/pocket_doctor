package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointment")
public class Appointment  extends BaseDomain {

    private  String doctorId;

    private  String userId;

    private  String appointmentDate;

    private  String appointmentTime;

    private  String description;

}

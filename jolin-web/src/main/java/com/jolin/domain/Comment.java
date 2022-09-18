package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Comment extends BaseDomain {

    private  Integer AppointmentId;

    private  Integer score;

    private  String comment;


}

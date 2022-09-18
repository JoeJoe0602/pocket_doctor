package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class InsurancePayment extends BaseDomain {

    private  Integer appointmentId;

    private  Integer status;


}

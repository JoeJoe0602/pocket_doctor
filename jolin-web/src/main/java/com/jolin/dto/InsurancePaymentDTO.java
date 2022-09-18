package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class InsurancePaymentDTO extends BaseDTO {

    private  Integer appointmentId;

    private  Integer status;
}

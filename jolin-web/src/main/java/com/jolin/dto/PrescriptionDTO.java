package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class PrescriptionDTO extends BaseDTO {

    private  String userId;

    private  String doctorId;

    private  String content;
}

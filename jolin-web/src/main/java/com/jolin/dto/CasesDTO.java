package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CasesDTO extends BaseDTO {

    private  String userId;

    private  String doctorId;

    private  String content;
}

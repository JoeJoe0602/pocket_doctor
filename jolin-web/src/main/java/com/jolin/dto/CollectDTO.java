package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CollectDTO extends BaseDTO {

    private  String userId;

    private  Integer foreignId;

    private  String type;
}

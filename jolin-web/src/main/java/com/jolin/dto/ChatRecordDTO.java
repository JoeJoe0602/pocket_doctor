package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class ChatRecordDTO extends BaseDTO {

    private  String doctorId;

    private  String userId;

    private  String content;

    private  String contentType;

    private  Integer isRead ;
}

package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class NotificationDTO extends BaseDTO {

    private  String userId;

    private  String content;

}

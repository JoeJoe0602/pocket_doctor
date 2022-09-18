package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CommentDTO extends BaseDTO {

    private  Integer AppointmentId;

    private  Integer score;

    private  String comment;
}

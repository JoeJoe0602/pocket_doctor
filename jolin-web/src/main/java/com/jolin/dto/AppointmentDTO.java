package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppointmentDTO extends BaseDTO {

    @ApiModelProperty(value = "Insert and modify need no value", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UserInfoDTO user;

    @ApiModelProperty(value = "Insert and modify need no value", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UserInfoDTO doctor;

    private String doctorId;

    private String userId;

    private String appointmentDate;

    private String appointmentTime;

    private String description;
}

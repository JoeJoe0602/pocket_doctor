package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import com.jolin.domain.Doctor;
import com.jolin.domain.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppointmentDTO extends BaseDTO {

    @ApiModelProperty(value = "新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UserInfoDTO user;

    @ApiModelProperty(value = "新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private UserInfoDTO doctor;

    private String doctorId;

    private String userId;

    private String appointmentDate;

    private String appointmentTime;

    private String description;
}

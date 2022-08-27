package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class BaseCommonDTO extends CommonDTO {
    @ApiModelProperty(value = "auto_increment id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer id;
}

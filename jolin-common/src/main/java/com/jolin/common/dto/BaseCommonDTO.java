package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class BaseCommonDTO extends CommonDTO {
    @ApiModelProperty(value = "ID", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;
}

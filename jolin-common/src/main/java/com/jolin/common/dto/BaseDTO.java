package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO<T extends BaseDTO> extends BaseCommonDTO {

    @ApiModelProperty(value = "sort", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer sort;

    @ApiModelProperty(value = "delete falg, 0 delete、1 not delete", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer isDelete;

    @ApiModelProperty(value = "createdAt", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime createdAt;

    @ApiModelProperty(value = "updatedAt", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime updatedAt;

}

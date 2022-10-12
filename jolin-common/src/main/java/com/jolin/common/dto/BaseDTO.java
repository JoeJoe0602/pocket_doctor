package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO<T extends BaseDTO> extends BaseCommonDTO {


    @ApiModelProperty(value = "Sort, insert and modify do not need to pass value", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected Integer sort;


    @ApiModelProperty(value = "Create time, insert and modify do not need to pass value", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime createdAt;

    @ApiModelProperty(value = "Update time, insert and modify do not need to pass value", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime updatedAt;

    @ApiModelProperty(value = "Logical deletion flag. 0 Yes It has been deleted. No value is required for insert and modify", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer isDelete;
}

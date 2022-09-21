package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO<T extends BaseDTO> extends BaseCommonDTO {


    @ApiModelProperty(value = "排序，新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected Integer sort;


    @ApiModelProperty(value = "创建时间，新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime createdAt;

    @ApiModelProperty(value = "修改时间，新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    protected LocalDateTime updatedAt;

    @ApiModelProperty(value = "逻辑删除标志。0是已删除，新增修改不需要传值", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer isDelete;
}

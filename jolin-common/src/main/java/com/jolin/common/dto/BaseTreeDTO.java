package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public abstract class BaseTreeDTO<T extends BaseTreeDTO> extends BaseDTO<T> {
    @ApiModelProperty(value = "parent id")
    private String parentId;

    @ApiModelProperty(value = "Tree structure DTO child object", hidden = true)
    private List<T> children;

    @ApiModelProperty(value = "Sort field")
    private Integer orderIndex;

    @ApiModelProperty(value = "path")
    private String path;

}

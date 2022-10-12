package com.jolin.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "Paging queries sort field objects", description = "Paging queries sort field objects")
@Data
@NoArgsConstructor
public final class SortDTO implements Serializable{
    private static final long serialVersionUID = 11L;

    @ApiModelProperty(value = "The name of the attribute to sort")
    private String fieldName;

    @ApiModelProperty(value = "ASC or DESC",example = "asc")
    private String direction;

}

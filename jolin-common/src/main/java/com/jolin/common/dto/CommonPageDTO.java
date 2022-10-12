package com.jolin.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Basic paging query object, does not depend on Base, native JPA or Mybatis can be directly used",
        description = "Base query object, does not depend on Base, native JPA or Mybatis can be used directly")
@Data
@NoArgsConstructor
public class CommonPageDTO<DTO> implements Serializable {

    @Min(value = 1, message = "At least the first page")
    @ApiModelProperty(example = "1")
    @NotNull(message = "Page numbers are necessary parameters")
    private Integer page;

    @Min(value = 1, message = "Display at least 1 item per page")
    @Max(value = 1000, message = "Display a maximum of 1000 items per page")
    @ApiModelProperty(example = "10")
    @NotNull(message = "Page size is a required parameter")
    private Integer pageSize;

    private List<SortDTO> sorts;

    private DTO filters;

    private Long total;

    @ApiModelProperty(example = "[]")
    private List list;
}

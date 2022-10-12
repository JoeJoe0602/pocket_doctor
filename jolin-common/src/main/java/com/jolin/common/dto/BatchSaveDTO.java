package com.jolin.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@ApiModel(value = "Save operation DTOs in batches", description = "Save operation DTOs in batches")
@Data
@NoArgsConstructor
public class BatchSaveDTO<DTO extends CommonDTO> implements Serializable {
    private static final long serialVersionUID = 11L;

    @ApiModelProperty(value = "The collection of association DTOs to delete")
    List<DTO> deleteDTOs;

    @ApiModelProperty(value = "The collection of association DTOs to create")
    List<DTO> createDTOs;
}
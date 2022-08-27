package com.jolin.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@ApiModel(value = "batch dto", description = "batch dto")
@Data
@NoArgsConstructor
public class BatchSaveDTO<DTO extends CommonDTO> implements Serializable {
    private static final long serialVersionUID = 11L;

    @ApiModelProperty(value = "delete batch dto")
    List<DTO> deleteDTOs;

    @ApiModelProperty(value = "add batch dto")
    List<DTO> createDTOs;
}
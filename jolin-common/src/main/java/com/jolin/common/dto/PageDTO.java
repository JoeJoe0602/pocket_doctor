package com.jolin.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "Paging query object", description = "Paging query object")
@Data
@NoArgsConstructor
public class PageDTO<DTO extends CommonDTO> extends CommonPageDTO<DTO> implements Serializable {

}

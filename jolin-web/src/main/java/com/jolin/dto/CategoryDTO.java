package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CategoryDTO extends BaseDTO {

    private  Integer parentId;

    private  String name;

    private  String icon;

    private  String categoryType;

}

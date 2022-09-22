package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class DictionaryTypeDTO extends BaseDTO {
    private  String name;

    private  Integer key;
}

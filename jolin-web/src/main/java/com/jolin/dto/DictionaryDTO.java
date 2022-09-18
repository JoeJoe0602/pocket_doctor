package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class DictionaryDTO extends BaseDTO {

    private  Integer dictionaryTypeId;

    private  String name;

    private  String code;
}

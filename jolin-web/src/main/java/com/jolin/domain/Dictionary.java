package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Dictionary extends BaseDomain {

    private  Integer dictionaryTypeId;

    private  String name;

    private  String code;



}

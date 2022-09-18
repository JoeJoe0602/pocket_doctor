package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class SystemConfig extends BaseDomain {

    private  String name;

    private  String code;

    private  String remark;


}

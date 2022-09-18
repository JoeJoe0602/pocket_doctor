package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Case extends BaseDomain {


    private  String userId;

    private  String doctorId;

    private  String content;


}

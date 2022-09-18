package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Collect extends BaseDomain {

    private  String userId;

    private  Integer foreignId;

    private  String type;


}

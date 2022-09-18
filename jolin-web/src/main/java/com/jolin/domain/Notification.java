package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Notification extends BaseDomain {


    private  String userId;

    private  String content;


}

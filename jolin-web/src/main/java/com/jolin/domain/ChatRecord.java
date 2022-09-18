package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class ChatRecord extends BaseDomain {

    private  String doctorId;

    private  String userId;

    private  String content;

    private  String contentType;

    private  Integer isRead;


}

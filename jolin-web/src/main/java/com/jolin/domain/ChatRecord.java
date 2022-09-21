package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_record")
public class ChatRecord extends BaseDomain {

    private  String doctorId;

    private  String userId;

    private  String content;

    private  String contentType;

    private  Integer isRead;


}

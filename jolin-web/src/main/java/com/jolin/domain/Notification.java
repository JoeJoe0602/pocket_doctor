package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("notification")
public class Notification extends BaseDomain {


    private  String userId;

    private  String content;


}

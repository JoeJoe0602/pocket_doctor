package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("collect")
public class Collect extends BaseDomain {

    private  String userId;

    private  Integer foreignId;

    private  String type;


}

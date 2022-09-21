package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cases")
public class Cases extends BaseDomain {


    private  String userId;

    private  String doctorId;


    private  String content;


}

package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category extends BaseDomain {

    private  Integer parentId;

    private  String name;

    private  String icon;

    private  String categoryType;


}

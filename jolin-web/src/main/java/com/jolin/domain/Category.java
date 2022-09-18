package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Category extends BaseDomain {

    private  Integer parentId;

    private  String name;

    private  String icon;

    private  String categoryType;


}

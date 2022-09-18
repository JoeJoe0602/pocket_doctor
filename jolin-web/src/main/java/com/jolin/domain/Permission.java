package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Permission extends BaseDomain {

    private  String parentId;

    private  String name;

    private  String description;

    private  String permissionKey;


}

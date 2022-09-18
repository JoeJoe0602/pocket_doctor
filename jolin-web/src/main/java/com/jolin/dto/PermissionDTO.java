package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class PermissionDTO extends BaseDTO {

    private  String parentId;

    private  String name;

    private  String description;

    private  String permissionKey;
}

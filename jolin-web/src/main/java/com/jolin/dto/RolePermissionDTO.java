package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class RolePermissionDTO extends BaseDTO {

    private Integer roleId;

    private Integer permissionId;


}

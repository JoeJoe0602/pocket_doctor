package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_permission")
public class RolePermission extends BaseDomain {
    private static final long serialVersionUID = 1L;
    

    @ApiModelProperty(value = "roleId")
    private Integer roleId;

    @ApiModelProperty(value = "permissionId")
    private Integer permissionId;
}

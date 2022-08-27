package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * 框架统一用户，所有系统需要有的用户基础属性
 */
@Data
@NoArgsConstructor
public abstract class CommonUserDTO extends BaseDTO {
    private static final long serialVersionUID = 11718455276020707L;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "是否启用（锁定），1是启用（不锁定），0是不启用（被锁定）", example = "1")
    private String state;


}

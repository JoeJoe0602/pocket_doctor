package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jolin.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo extends BaseDomain {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "登录名")
    private String loginName;

    private String password;

    @ApiModelProperty(value = "真实姓名")
    @TableField(value = "nickname", fill = FieldFill.INSERT)
    private String nickName;



    @ApiModelProperty(value = "是否启用（锁定），1是启用（不锁定），0是不启用（被锁定）", example = "1")
    private String state;

    private Integer roleId;

    private String photoUrl;

    private Integer isAdmin;


}


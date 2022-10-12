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



@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo extends BaseDomain {

    private static final long serialVersionUID=1L;


    @ApiModelProperty(value = "loginName")
    private String loginName;

    private String password;

    @ApiModelProperty(value = "nickName")
    @TableField(value = "nickname", fill = FieldFill.INSERT)
    private String nickName;



    @ApiModelProperty(value = "Enabled or not (locked), 1 is enabled (not locked), 0 is not enabled (locked)", example = "1")
    private String state;

    private Integer roleId;

    private String photoUrl;

    private Integer isAdmin;


}


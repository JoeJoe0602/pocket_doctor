package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
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

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private String username;

    private String password;

    private String nickname;

    private String photoUrl;

    private String isAdmin;

    private String state;

}


package com.jolin.dto;

import com.jolin.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "userDto对象", description = "用户对象userDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;



    @ApiModelProperty(value = "登录名")
    private String loginName;

    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String nickName;



    @ApiModelProperty(value = "是否启用（锁定），1是启用（不锁定），0是不启用（被锁定）", example = "1")
    private String state;


    private Integer roleId;

    private String photoUrl;

    private Integer isAdmin;





}


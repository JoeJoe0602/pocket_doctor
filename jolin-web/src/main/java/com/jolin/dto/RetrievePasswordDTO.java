package com.jolin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author jolin
 * @version 1.0
 * @date 2021/8/25
 * @describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePasswordDTO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;



    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码")
    private String confirm;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;


}

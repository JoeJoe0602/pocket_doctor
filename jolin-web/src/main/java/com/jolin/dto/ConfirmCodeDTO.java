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
public class ConfirmCodeDTO {
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "手机号")
    private String phoneNum;
    @ApiModelProperty(value = "0 手机验证码 1 邮箱 2 邮箱注册验证码 3 手机注册验证码")
    @Max(value=3,message = "type只能为0-3")
    @Min(value=0,message = "type只能为0-3")
    private Integer type;
}

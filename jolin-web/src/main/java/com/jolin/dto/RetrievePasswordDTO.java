package com.jolin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/8/25
 * @describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePasswordDTO {
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码")
    private String confirm;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "0 手机验证码 1 邮箱")
    @Max(value=1,message = "type只能为0和1")
    @Min(value=0,message = "type只能为0和1")
    private Integer type;
}

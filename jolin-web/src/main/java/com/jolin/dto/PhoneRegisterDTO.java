package com.jolin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/8/30
 * @describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRegisterDTO {
    @NotBlank(message = "phoneNum不能为空")
    private String phoneNum;
    @NotBlank(message = "code不能为空")
    private String code;
    @NotBlank(message = "username不能为空")
    private String username;
    @NotBlank(message = "password不能为空")
    private String password;

}

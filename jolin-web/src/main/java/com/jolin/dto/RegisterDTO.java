package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDTO extends BaseDTO {


   private Integer roleId;

   @NotBlank(message = "loginName cannot be null!")
   private  String loginName;

   @NotBlank(message = "nickname cannot be null!")
   private  String nickname;

   @NotBlank(message = "password cannot be null!")
   private  String password;

   @NotBlank(message = "confirmPassword cannot be null!")
   private  String confirmPassword;
}

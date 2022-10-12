package com.jolin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePasswordDTO {

    @NotBlank(message = "The username cannot be empty")
    @ApiModelProperty(value = "username")
    private String loginName;

    @NotBlank(message = "The password cannot be empty")
    @ApiModelProperty(value = "password")
    private String password;



    @NotBlank(message = "The confirm password cannot be empty")
    @ApiModelProperty(value = "confirm password")
    private String confirm;

    @NotBlank(message = "The old password cannot be empty")
    @ApiModelProperty(value = "old password")
    private String oldPassword;


}

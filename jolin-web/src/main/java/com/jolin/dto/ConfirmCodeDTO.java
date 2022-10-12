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
public class ConfirmCodeDTO {
    @NotBlank(message = "The verification code cannot be empty")
    @ApiModelProperty(value = "verification code ")
    private String code;
    @ApiModelProperty(value = "email")
    private String email;
    @ApiModelProperty(value = "phone")
    private String phoneNum;
    @ApiModelProperty(value = "0 verification code 1 email 2 email registration verification code  3 phone registration verification code")
    @Max(value=3,message = "The value of type can be 0-3")
    @Min(value=0,message = "The value of type can be 0-3")
    private Integer type;
}

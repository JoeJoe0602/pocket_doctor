package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import com.jolin.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 * Class Name：UserInfoDTO Class Description： UserInfoDTO
 */
@ApiModel(value = "roleIdObject", description = "RoleObjectroleDto")
@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO {


    @ApiModelProperty(value = "RoleName")
    @NotBlank(message = "RoleName cannot be empty")
    private String name;

    @ApiModelProperty(value = "Description")
    private String description;

}


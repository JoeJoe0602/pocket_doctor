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
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "roleId对象", description = "角色对象roleDto")
@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO {


    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

}


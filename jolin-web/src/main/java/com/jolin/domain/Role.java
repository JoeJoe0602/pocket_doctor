package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role extends BaseDomain {
    private static final long serialVersionUID = 1L;
    

    @ApiModelProperty(value = "roleName")
    private String name;

    @ApiModelProperty(value = "roleDescription")
    private String description;
}

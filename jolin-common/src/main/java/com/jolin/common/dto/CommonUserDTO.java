package com.jolin.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Framework unified user, all systems need to have user base attributes
 */
@Data
@NoArgsConstructor
public abstract class CommonUserDTO extends BaseDTO {
    private static final long serialVersionUID = 11718455276020707L;

    @ApiModelProperty(value = "loginName")
    private String loginName;

    private String password;

    @ApiModelProperty(value = "nickName")
    private String nickName;



    @ApiModelProperty(value = "Enabled or not (locked), 1 is enabled (not locked), 0 is not enabled (locked)", example = "1")
    private String state;

}

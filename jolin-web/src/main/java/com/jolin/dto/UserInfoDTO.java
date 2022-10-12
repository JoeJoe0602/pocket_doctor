package com.jolin.dto;

import com.jolin.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;



@ApiModel(value = "userDtoObject", description = "UserObjectuserDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;



    @ApiModelProperty(value = "loginName")
    private String loginName;

    private String password;

    @ApiModelProperty(value = "nickName")
    private String nickName;



    @ApiModelProperty(value = "Enabled or not (locked), 1 is enabled (not locked), 0 is not enabled (locked)）", example = "1")
    private String state;


    private Integer roleId;

    private String photoUrl;

    private Integer isAdmin;

    private String confirmPassword;





}


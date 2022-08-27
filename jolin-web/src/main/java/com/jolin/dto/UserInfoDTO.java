package com.jolin.dto;

import com.jolin.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "userDto对象", description = "用户对象userDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;


    @ApiModelProperty(value = "用户排序")
    private Integer roleId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String photo_url;

    @ApiModelProperty(value = "是否管理员")
    private Integer isAdmin;

}


package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_group")
public class UserGroup extends BaseDomain {

    private static final long serialVersionUID=1L;

    private Integer groupIndex;

    private String groupName;

    private String state;


}

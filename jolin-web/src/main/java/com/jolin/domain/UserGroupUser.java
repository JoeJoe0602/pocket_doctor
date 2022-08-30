package com.jolin.domain;

import com.jolin.common.annotation.BaseJoinId;
import com.jolin.common.base.BaseJoinDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_group_user")
public class UserGroupUser extends BaseJoinDomain {

    private static final long serialVersionUID=1L;
    @BaseJoinId(index=BaseJoinId.Index.first)
    private String groupId;

    @BaseJoinId(index=BaseJoinId.Index.second)
    private String userId;

}
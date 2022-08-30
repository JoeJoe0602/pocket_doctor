package com.jolin.service.impl;

import com.jolin.common.base.BaseJoinServiceImpl;
import com.jolin.domain.UserGroupUser;
import com.jolin.dto.UserGroupUserJoinDTO;
import com.jolin.mapper.UserGroupUserMapper;
import com.jolin.service.IUserGroupUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupUserServiceImpl extends BaseJoinServiceImpl<UserGroupUserMapper, UserGroupUser, UserGroupUserJoinDTO>
        implements IUserGroupUserService<UserGroupUser> {

    @Override
    public List<String> findGroupIdsByUserIds(List<String> userIds) {
        return super.findFirstIdsBySecondIds(userIds);
    }

    @Override
    public List<String> findUserIdsByGroupIds(List<String> groupIds) {
        return super.findSecondIdsByFirstIds(groupIds);
    }
}
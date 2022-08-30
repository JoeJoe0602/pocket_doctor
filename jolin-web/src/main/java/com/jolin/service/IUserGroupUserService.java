package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseJoinService;
import com.jolin.dto.UserGroupUserJoinDTO;

import java.util.List;

public interface IUserGroupUserService<D extends CommonDomain> extends IBaseJoinService<UserGroupUserJoinDTO, D> {

    List<String> findUserIdsByGroupIds(List<String> groupIds);
    List<String> findGroupIdsByUserIds(List<String> userIds);
}

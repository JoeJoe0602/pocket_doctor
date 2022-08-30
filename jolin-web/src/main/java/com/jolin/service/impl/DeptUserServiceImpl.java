package com.jolin.service.impl;

import com.jolin.common.base.BaseJoinServiceImpl;
import com.jolin.domain.DeptUser;
import com.jolin.dto.DeptUserJoinDTO;
import com.jolin.mapper.DeptUserMapper;
import com.jolin.service.IDeptUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptUserServiceImpl extends BaseJoinServiceImpl<DeptUserMapper, DeptUser, DeptUserJoinDTO>
        implements IDeptUserService<DeptUser> {

    @Override
    public List<String> findDeptIdsByUserIds(List<String> userIds) {
        return super.findFirstIdsBySecondIds(userIds);
    }

    @Override
    public List<String> findUserIdsByDeptIds(List<String> deptIds) {
        return super.findSecondIdsByFirstIds(deptIds);
    }
}
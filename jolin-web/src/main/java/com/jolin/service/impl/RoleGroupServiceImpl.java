package com.jolin.service.impl;

import com.jolin.common.base.BaseJoinServiceImpl;
import com.jolin.domain.RoleGroup;
import com.jolin.dto.RoleGroupJoinDTO;
import com.jolin.mapper.RoleGroupMapper;
import com.jolin.service.IRoleGroupService;
import org.springframework.stereotype.Service;

@Service
public class RoleGroupServiceImpl extends BaseJoinServiceImpl<RoleGroupMapper, RoleGroup, RoleGroupJoinDTO>
        implements IRoleGroupService<RoleGroup> {

}
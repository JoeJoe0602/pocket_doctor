package com.jolin.mapper;

import com.jolin.common.base.IBaseJoinMapper;
import com.jolin.domain.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends IBaseJoinMapper<RoleMenu> {

    void deleteByMenuIdsAndRoleId(List<String> menuIds, String roleId);
}

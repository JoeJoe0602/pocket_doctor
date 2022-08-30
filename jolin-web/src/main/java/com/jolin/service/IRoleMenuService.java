package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseJoinService;
import com.jolin.dto.RoleMenuJoinDTO;

import java.util.List;

public interface IRoleMenuService<D extends CommonDomain> extends IBaseJoinService<RoleMenuJoinDTO, D> {

    /**
     * 保存角色菜单的分配
     */
    void saveRoleMenusForRest(String[] menuIds, List preMenuIds, String roleId);

    List<String> findRoleIdsByMenuIds(List<String> menuIds);

    /**
     * 根据roleId查询出关系表中所有的菜单Id
     */
    List<String> findMenuIdsByRoleIds(List<String> roleIds);

    void unbindRoleMenu(String menuId, String roleId);
}

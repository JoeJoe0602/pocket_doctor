package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseJoinService;
import com.jolin.dto.RoleUserJoinDTO;

import java.util.List;

public interface IRoleUserService<D extends CommonDomain> extends IBaseJoinService<RoleUserJoinDTO, D> {

    /**
     * 保存角色用户的分配
     */
    void saveRoleUsers(String[] userIds, String preUserIds, String roleId);

    List<String> findUserIdsByRoleIds(List<String> roleIds);

    List<String> findRoleIdsByUserIds(List<String> userIds);
}

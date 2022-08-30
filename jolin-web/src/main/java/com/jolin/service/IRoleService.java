package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.dto.RoleDTO;

/**
 * 类名称：角色Service
 */
public interface IRoleService<D extends CommonDomain> extends IBaseService<RoleDTO, D> {
    /**
     * 根据角色名称查询角色详情
     */
    RoleDTO findByRoleName(String name);
}

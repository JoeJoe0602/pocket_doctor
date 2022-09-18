package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.RolePermission;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends IBaseMapper<RolePermission> {


    IPage<RolePermission> getPage(@Param("page") Page page, @Param("role_permission") RolePermission  rolePermission);

}

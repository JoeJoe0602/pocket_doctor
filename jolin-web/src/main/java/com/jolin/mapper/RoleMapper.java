package com.jolin.mapper;

import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends IBaseMapper<Role> {

    /**
     *  分页
     */
    IPage<Role> selectPage(@Param("page") Page page, @Param("role") Role role);

    Role findByRoleName(String roleName);
}

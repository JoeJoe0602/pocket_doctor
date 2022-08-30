package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Role;
import com.jolin.log.domain.WebLogs;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends IBaseMapper<Role> {

    IPage<Role> getPage(@Param("page") Page page, @Param("role") Role role);

}

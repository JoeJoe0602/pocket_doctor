package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Permission;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper extends IBaseMapper<Permission> {


    IPage<Permission> getPage(@Param("page") Page page, @Param("permission") Permission  permission);


}

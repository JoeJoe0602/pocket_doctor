package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.SystemConfig;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigMapper extends IBaseMapper<SystemConfig> {


    IPage<SystemConfig> getPage(@Param("page") Page page, @Param("system_config") SystemConfig  systemConfig);

}

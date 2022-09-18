package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Log;
import org.apache.ibatis.annotations.Param;

public interface LogMapper extends IBaseMapper<Log> {


    IPage<Log> getPage(@Param("page") Page page, @Param("log") Log  log);

}

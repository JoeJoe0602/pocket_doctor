package com.jolin.log.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.log.domain.WebLogs;
import org.apache.ibatis.annotations.Param;

public interface WebLogsMapper extends IBaseMapper<WebLogs> {

    /**
     *  Page
     */
    IPage<WebLogs> getPage(@Param("page") Page page, @Param("webLogs") WebLogs webLogs);

}

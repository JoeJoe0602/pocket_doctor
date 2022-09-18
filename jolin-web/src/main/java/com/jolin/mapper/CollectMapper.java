package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper extends IBaseMapper<Collect> {


    IPage<Collect> getPage(@Param("page") Page page, @Param("collect") Collect  collect);

}

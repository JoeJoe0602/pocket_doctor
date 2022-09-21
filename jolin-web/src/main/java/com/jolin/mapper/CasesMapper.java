package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Cases;
import org.apache.ibatis.annotations.Param;

public interface CasesMapper extends IBaseMapper<Cases> {


    IPage<Cases> getPage(@Param("page") Page page, @Param("cases") Cases  casemodel);

}

package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Case;
import org.apache.ibatis.annotations.Param;

public interface CaseMapper extends IBaseMapper<Case> {


    IPage<Case> getPage(@Param("page") Page page, @Param("case") Case  casemodel);

}

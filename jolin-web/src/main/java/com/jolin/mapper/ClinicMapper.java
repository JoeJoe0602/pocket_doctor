package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Clinic;
import org.apache.ibatis.annotations.Param;

public interface ClinicMapper extends IBaseMapper<Clinic> {


    IPage<Clinic> getPage(@Param("page") Page page, @Param("clinic") Clinic clinic);

    IPage<Clinic> getPageDistance(@Param("page") Page page, @Param("clinic") Clinic clinic);

}

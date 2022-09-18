package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Prescription;
import org.apache.ibatis.annotations.Param;

public interface PrescriptionMapper extends IBaseMapper<Prescription> {


    IPage<Prescription> getPage(@Param("page") Page page, @Param("prescription") Prescription  prescription);

}

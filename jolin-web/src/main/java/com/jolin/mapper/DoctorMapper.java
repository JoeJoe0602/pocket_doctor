package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Doctor;
import org.apache.ibatis.annotations.Param;

public interface DoctorMapper extends IBaseMapper<Doctor> {


    IPage<Doctor> getPage(@Param("page") Page page, @Param("doctor") Doctor  doctor);

}

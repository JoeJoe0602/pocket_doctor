package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Appointment;
import org.apache.ibatis.annotations.Param;

public interface AppointmentMapper extends IBaseMapper<Appointment> {


    IPage<Appointment> getPage(@Param("page") Page page, @Param("appointment") Appointment  appointment);

}

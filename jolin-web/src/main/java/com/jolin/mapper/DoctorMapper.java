package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.ChatRecord;
import com.jolin.domain.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorMapper extends IBaseMapper<Doctor> {


    IPage<Doctor> getPage(@Param("page") Page page, @Param("doctor") Doctor  doctor);
    IPage<Doctor> getPageDistance(@Param("page") Page page, @Param("doctor") Doctor  doctor);

    List<ChatRecord> getChatRecord(@Param("chatRecord") ChatRecord chatRecord);

}

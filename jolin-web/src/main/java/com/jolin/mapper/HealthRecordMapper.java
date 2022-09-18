package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.HealthRecord;
import org.apache.ibatis.annotations.Param;

public interface HealthRecordMapper extends IBaseMapper<HealthRecord> {

    IPage<HealthRecord> getPage(@Param("page") Page page, @Param("healthRecord") HealthRecord healthRecord);

}

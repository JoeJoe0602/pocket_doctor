package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.ChatRecord;
import org.apache.ibatis.annotations.Param;

public interface ChatRecordMapper extends IBaseMapper<ChatRecord> {


    IPage<ChatRecord> getPage(@Param("page") Page page, @Param("chatrecord")  ChatRecord chatrecord);

}

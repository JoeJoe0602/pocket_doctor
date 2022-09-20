package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.ChatRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatRecordMapper extends IBaseMapper<ChatRecord> {


    IPage<ChatRecord> getPage(@Param("page") Page page, @Param("chat_record")  ChatRecord chatrecord);
    List<ChatRecord>  getChatRecordList( @Param("chat_record")  ChatRecord chatrecord);
}

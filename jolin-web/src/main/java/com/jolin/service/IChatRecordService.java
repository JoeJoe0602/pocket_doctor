package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.domain.ChatRecord;
import com.jolin.dto.ChatRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Service
 */
public interface IChatRecordService<D extends CommonDomain> extends IBaseService<ChatRecordDTO, D> {

 List<ChatRecord> getChatRecord(ChatRecord chatRecord);

 List<ChatRecord>  getChatRecordList( ChatRecord chatrecord);
}
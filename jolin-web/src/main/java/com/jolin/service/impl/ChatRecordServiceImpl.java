package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.ChatRecord;
import com.jolin.dto.ChatRecordDTO;
import com.jolin.mapper.ChatRecordMapper;
import com.jolin.service.IChatRecordService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChatRecordServiceImpl extends BaseServiceImpl<ChatRecordMapper, ChatRecord, ChatRecordDTO> implements IChatRecordService<ChatRecord> {


    @Override
    public PageDTO getPage(PageDTO<ChatRecordDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        ChatRecord chatRecord = getDomainFilterFromPageDTO(pageDTO);
        IPage<ChatRecord> chatRecordIPage = iBaseRepository.getPage(page, chatRecord);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(chatRecordIPage, ChatRecordDTO.class, pageDTO);
        return resultPage;
    }

    @Override
    public List<ChatRecord> getChatRecord(ChatRecord chatRecord) {
        QueryWrapper<ChatRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", chatRecord.getUserId());
        wrapper.eq("doctor_id", chatRecord.getDoctorId());
        return iBaseRepository.selectList(wrapper);
    }

    @Override
    public List<ChatRecord> getChatRecordList(ChatRecord chatrecord) {

        return iBaseRepository.getChatRecordList(chatrecord) ;
    }
}

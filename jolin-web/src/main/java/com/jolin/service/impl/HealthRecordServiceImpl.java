package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.ChatRecord;
import com.jolin.domain.HealthRecord;
import com.jolin.domain.Role;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.dto.RoleDTO;
import com.jolin.mapper.HealthRecordMapper;
import com.jolin.mapper.RoleMapper;
import com.jolin.service.IHealthRecordService;
import com.jolin.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HealthRecordServiceImpl extends BaseServiceImpl<HealthRecordMapper, HealthRecord, HealthRecordDTO> implements IHealthRecordService<HealthRecord> {


    @Override
    public PageDTO getPage(PageDTO<HealthRecordDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        HealthRecord healthRecord = getDomainFilterFromPageDTO(pageDTO);
        IPage<HealthRecord> roleIPage = iBaseRepository.getPage(page, healthRecord);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(roleIPage, HealthRecordDTO.class, pageDTO);
        return resultPage;
    }

    @Override
    public List<HealthRecord> getHealthRecord(HealthRecord healthRecord) {
        QueryWrapper<HealthRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", healthRecord.getUserId());
        return iBaseRepository.selectList(wrapper);
    }
}

package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Log;
import com.jolin.dto.LogDTO;
import com.jolin.mapper.LogMapper;
import com.jolin.service.ILogService;
import org.springframework.stereotype.Service;


@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log, LogDTO> implements ILogService<Log> {


    @Override
    public PageDTO getPage(PageDTO<LogDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Log log = getDomainFilterFromPageDTO(pageDTO);
        IPage<Log> logIPage = iBaseRepository.getPage(page, log);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(logIPage, LogDTO.class, pageDTO);
        return resultPage;
    }
}

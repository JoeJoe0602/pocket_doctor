package com.jolin.log.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.log.domain.WebLogs;
import com.jolin.log.dto.WebLogsDTO;
import com.jolin.log.mapper.WebLogsMapper;
import com.jolin.log.service.IWebLogsService;
import org.springframework.stereotype.Service;

@Service
public class WebLogsServiceImpl extends BaseServiceImpl<WebLogsMapper, WebLogs, WebLogsDTO> implements IWebLogsService<WebLogs> {

    @Override
    public PageDTO getPage(PageDTO<WebLogsDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        WebLogs webLogs = getDomainFilterFromPageDTO(pageDTO);
        IPage<WebLogs> webLogsIPage = iBaseRepository.getPage(page, webLogs);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(webLogsIPage,WebLogsDTO.class,pageDTO);
        return resultPage;
    }
}

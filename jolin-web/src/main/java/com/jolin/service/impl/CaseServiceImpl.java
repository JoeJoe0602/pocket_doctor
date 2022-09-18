package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Case;
import com.jolin.dto.CaseDTO;
import com.jolin.mapper.CaseMapper;
import com.jolin.service.ICaseService;
import org.springframework.stereotype.Service;


@Service
public class CaseServiceImpl extends BaseServiceImpl<CaseMapper, Case, CaseDTO> implements ICaseService<Case> {


    @Override
    public PageDTO getPage(PageDTO<CaseDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Case casemodel = getDomainFilterFromPageDTO(pageDTO);
        IPage<Case> caseIPage = iBaseRepository.getPage(page, casemodel);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(caseIPage, CaseDTO.class, pageDTO);
        return resultPage;
    }
}

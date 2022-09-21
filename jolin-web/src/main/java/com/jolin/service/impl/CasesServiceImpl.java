package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Cases;
import com.jolin.dto.CasesDTO;
import com.jolin.mapper.CasesMapper;
import com.jolin.service.ICasesService;
import org.springframework.stereotype.Service;


@Service
public class CasesServiceImpl extends BaseServiceImpl<CasesMapper, Cases, CasesDTO> implements ICasesService<Cases> {


    @Override
    public PageDTO getPage(PageDTO<CasesDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Cases casesmodel = getDomainFilterFromPageDTO(pageDTO);
        IPage<Cases> caseIPage = iBaseRepository.getPage(page, casesmodel);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(caseIPage, CasesDTO.class, pageDTO);
        return resultPage;
    }
}

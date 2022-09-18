package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Clinic;
import com.jolin.dto.ClinicDTO;
import com.jolin.mapper.ClinicMapper;
import com.jolin.service.IClinicService;
import org.springframework.stereotype.Service;


@Service
public class ClinicServiceImpl extends BaseServiceImpl<ClinicMapper, Clinic, ClinicDTO> implements IClinicService<Clinic> {


    @Override
    public PageDTO getPage(PageDTO<ClinicDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Clinic clinic = getDomainFilterFromPageDTO(pageDTO);
        IPage<Clinic> clinicIPage = iBaseRepository.getPage(page, clinic);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(clinicIPage, ClinicDTO.class, pageDTO);
        return resultPage;
    }
}

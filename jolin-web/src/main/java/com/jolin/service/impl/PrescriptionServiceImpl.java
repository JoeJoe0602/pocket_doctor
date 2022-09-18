package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Prescription;
import com.jolin.dto.PrescriptionDTO;
import com.jolin.mapper.PrescriptionMapper;
import com.jolin.service.IPrescriptionService;
import org.springframework.stereotype.Service;


@Service
public class PrescriptionServiceImpl extends BaseServiceImpl<PrescriptionMapper, Prescription, PrescriptionDTO> implements IPrescriptionService<Prescription> {


    @Override
    public PageDTO getPage(PageDTO<PrescriptionDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Prescription prescription = getDomainFilterFromPageDTO(pageDTO);
        IPage<Prescription> prescriptionIPage = iBaseRepository.getPage(page, prescription);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(prescriptionIPage, PrescriptionDTO.class, pageDTO);
        return resultPage;
    }
}

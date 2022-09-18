package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.InsurancePayment;
import com.jolin.dto.InsurancePaymentDTO;
import com.jolin.mapper.InsurancePaymentMapper;
import com.jolin.service.IInsurancePaymentService;
import org.springframework.stereotype.Service;


@Service
public class InsurancePaymentServiceImpl extends BaseServiceImpl<InsurancePaymentMapper, InsurancePayment, InsurancePaymentDTO> implements IInsurancePaymentService<InsurancePayment> {


    @Override
    public PageDTO getPage(PageDTO<InsurancePaymentDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        InsurancePayment insurancePayment = getDomainFilterFromPageDTO(pageDTO);
        IPage<InsurancePayment> insurancePaymentIPage = iBaseRepository.getPage(page, insurancePayment);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(insurancePaymentIPage, InsurancePaymentDTO.class, pageDTO);
        return resultPage;
    }
}

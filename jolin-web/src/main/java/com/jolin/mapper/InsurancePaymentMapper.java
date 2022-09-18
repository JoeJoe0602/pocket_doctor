package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.InsurancePayment;
import org.apache.ibatis.annotations.Param;

public interface InsurancePaymentMapper extends IBaseMapper<InsurancePayment> {


    IPage<InsurancePayment> getPage(@Param("page") Page page, @Param("insurance_payment") InsurancePayment insurancePayment );

}

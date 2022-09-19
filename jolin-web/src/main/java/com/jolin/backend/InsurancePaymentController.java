package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.InsurancePaymentDTO;
import com.jolin.service.IInsurancePaymentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2.角色接口"})
@ApiSort(2)
@RestController
@RequestMapping("sys/insurance_payment")
public class InsurancePaymentController extends BaseController<IInsurancePaymentService, InsurancePaymentDTO> {




}

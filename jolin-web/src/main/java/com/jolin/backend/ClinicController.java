package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.ClinicDTO;
import com.jolin.service.IClinicService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Clinic"})
@ApiSort(16)
@RestController
@RequestMapping("sys/clinic")
public class ClinicController extends BaseController<IClinicService, ClinicDTO> {




}

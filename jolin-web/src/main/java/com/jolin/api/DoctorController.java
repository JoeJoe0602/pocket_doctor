package com.jolin.api;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.DoctorDTO;
import com.jolin.service.IDoctorService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2.角色接口"})
@ApiSort(2)
@RestController
@RequestMapping("sys/doctor")
public class DoctorController extends BaseController<IDoctorService, DoctorDTO> {




}

package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.AppointmentDTO;
import com.jolin.service.IAppointmentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Appointment"})
@ApiSort(10)
@RestController
@RequestMapping("sys/appointment")
public class AppointmentController extends BaseController<IAppointmentService, AppointmentDTO> {




}

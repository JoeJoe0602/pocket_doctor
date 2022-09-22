package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.PrescriptionDTO;
import com.jolin.service.IPrescriptionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Prescription"})
@ApiSort(25)
@RestController
@RequestMapping("sys/prescription")
public class PrescriptionController extends BaseController<IPrescriptionService, PrescriptionDTO> {




}

package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.service.IHealthRecordService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"HealthRecord"})
@ApiSort(22)
@RestController
@RequestMapping("sys/health_record")
public class HealthRecordController extends BaseController<IHealthRecordService, HealthRecordDTO> {
}

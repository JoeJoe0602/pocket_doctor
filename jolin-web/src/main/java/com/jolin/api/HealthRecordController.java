package com.jolin.api;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.dto.RoleDTO;
import com.jolin.service.IHealthRecordService;
import com.jolin.service.IRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2.角色接口"})
@ApiSort(2)
@RestController
@RequestMapping("sys/health_record")
public class HealthRecordController extends BaseController<IHealthRecordService, HealthRecordDTO> {
}

package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.SystemConfigDTO;
import com.jolin.service.ISystemConfigService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅SystemConfig"})
@ApiSort(27)
@RestController
@RequestMapping("sys/system_config")
public class SystemConfigController extends BaseController<ISystemConfigService, SystemConfigDTO> {




}

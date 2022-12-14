package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.PermissionDTO;
import com.jolin.service.IPermissionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Permission"})
@ApiSort(25)
@RestController
@RequestMapping("sys/permission")
public class PermissionController extends BaseController<IPermissionService, PermissionDTO> {




}

package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.RolePermissionDTO;
import com.jolin.service.IRolePermissionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"RolePermission"})
@ApiSort(26)
@RestController
@RequestMapping("sys/role_permission")
public class RolePermissionController extends BaseController<IRolePermissionService, RolePermissionDTO> {




}

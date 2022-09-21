package com.jolin.backend;


import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.RoleDTO;
import com.jolin.service.IRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Role"})
@ApiSort(25)
@RestController
@RequestMapping("sys/role")
public class RoleController extends BaseController<IRoleService, RoleDTO> {
}

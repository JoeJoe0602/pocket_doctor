package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.CasesDTO;
import com.jolin.service.ICasesService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Cases"})
@ApiSort(13)
@RestController
@RequestMapping("sys/cases")
public class CasesController extends BaseController<ICasesService, CasesDTO> {




}

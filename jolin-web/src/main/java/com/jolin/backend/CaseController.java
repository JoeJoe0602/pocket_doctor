package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.CaseDTO;
import com.jolin.service.ICaseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Case"})
@ApiSort(13)
@RestController
@RequestMapping("sys/case")
public class CaseController extends BaseController<ICaseService, CaseDTO> {




}

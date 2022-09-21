package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.DictionaryDTO;
import com.jolin.service.IDictionaryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Dictionary"})
@ApiSort(19)
@RestController
@RequestMapping("sys/dictionary")
public class DictionaryController extends BaseController<IDictionaryService, DictionaryDTO> {




}

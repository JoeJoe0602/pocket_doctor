package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.DictionaryTypeDTO;
import com.jolin.service.IDictionaryTypeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"DictionaryType"})
@ApiSort(20)
@RestController
@RequestMapping("sys/dictionary_type")
public class DictionaryTypeController extends BaseController<IDictionaryTypeService, DictionaryTypeDTO> {




}

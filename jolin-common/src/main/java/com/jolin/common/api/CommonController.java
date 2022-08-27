package com.jolin.common.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jolin.common.dto.CommonDTO;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.service.ICommonService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public abstract class CommonController<S extends ICommonService, DTO extends CommonDTO> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected S iBaseService;

    @Autowired
    protected MessageSource messageSource;

    @ApiOperation(value = "7.GET_PAGE_LIMIT")
    @ApiOperationSupport(order = 7)
    @PostMapping(value = "/search")
    public ResultDTO<PageDTO<DTO>> selectByPage(@RequestBody @Valid PageDTO<DTO> pageDTO) {
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }
}

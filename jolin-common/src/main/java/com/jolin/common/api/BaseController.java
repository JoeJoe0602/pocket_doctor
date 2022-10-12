package com.jolin.common.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jolin.common.dto.BaseDTO;
import com.jolin.common.dto.CommonDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.IBaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public abstract class BaseController<S extends IBaseService, DTO extends BaseDTO> extends CommonController<S, DTO> {

    @ApiOperation(value = "1.Insert")
    @ApiOperationSupport(order = 1)
    @PostMapping
    public ResultDTO<DTO> create(@RequestBody @Valid DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.create(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "2.Batch insert")
    @ApiOperationSupport(order = 2)
    @PostMapping("/batchCreate")
    public ResultDTO batchCreate(@RequestBody @Valid List<DTO> dtos) throws BaseException {
        iBaseService.batchCreate(dtos);
        return new ResultDTO();
    }

    @ApiOperation(value = "3.Modify")
    @ApiOperationSupport(order = 3)
    @PutMapping
    public ResultDTO<String> update(@RequestBody @Valid DTO dto) throws BaseException {
        DTO resultDTO = (DTO) iBaseService.update(dto);
        return new ResultDTO(resultDTO);
    }

    @ApiOperation(value = "4.Delete by id")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/{id}")
    public ResultDTO<Boolean> deleteById(@PathVariable("id") @NotBlank String id) throws BaseException {
        Boolean isSuccess = iBaseService.removeById(id);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "5.Batch delete based on multiple ids")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/deleteByIds")
    public ResultDTO<Boolean> deleteByIds(@RequestBody List<String> ids) throws BaseException {
        Boolean isSuccess = iBaseService.removeByIds(ids);
        return new ResultDTO<>(isSuccess);
    }

    @ApiOperation(value = "6.Query by id")
    @ApiOperationSupport(order = 6)
    @GetMapping("/{id}")
    public ResultDTO<DTO> selectById(@PathVariable("id") String id) throws BaseException {
        CommonDTO byId = iBaseService.findById(id);
        return new ResultDTO(byId);
    }

}

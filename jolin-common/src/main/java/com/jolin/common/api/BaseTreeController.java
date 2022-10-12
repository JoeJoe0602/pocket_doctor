package com.jolin.common.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jolin.common.dto.BaseTreeDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.IBaseTreeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseTreeController<S extends IBaseTreeService, DTO extends BaseTreeDTO> extends BaseController<S, DTO> {

    @ApiOperation(value = "1.Find all children by parentId and all levels if empty")
    @ApiOperationSupport(order = 1)
    @GetMapping("/find-by-pid")
    public ResultDTO<List<DTO>> findByParentId(@Valid String parentId) throws BaseException {
        List<DTO> children = iBaseService.findByParentId(parentId);
        return new ResultDTO(children);
    }

    @ApiOperation(value = "2.Check parent by child id")
    @ApiOperationSupport(order = 2)
    @GetMapping("/find-parent-by-id")
    public ResultDTO<DTO> findParentById(@Valid String id) throws BaseException {
        DTO child = (DTO) iBaseService.findById(id);
        DTO parent = (DTO) iBaseService.findById(child.getParentId());
        return new ResultDTO(parent);
    }

    @GetMapping("/move")
    @ApiOperation(value = "10.Node mobility interface")
    @ApiOperationSupport(order = 10)
    public ResultDTO<Boolean> move(@RequestParam String currentParentId, @RequestParam String targetParentId, @RequestParam String currentId) {
        Boolean move = iBaseService.move(currentParentId, targetParentId, currentId);
        return new ResultDTO(move);
    }

    @GetMapping("/find-children-by-pid")
    @ApiOperation(value = "11.Query the whole tree according to parentId")
    @ApiOperationSupport(order = 11)
    public ResultDTO<List<DTO>> findChildrenByPath(@RequestParam String parentId) {
        List childrenByPath = iBaseService.findChildrenByParentId(parentId);
        return new ResultDTO(childrenByPath);
    }
}

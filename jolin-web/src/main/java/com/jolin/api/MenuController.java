package com.jolin.api;import com.github.xiaoymin.knife4j.annotations.ApiSupport;import com.jolin.common.api.BaseTreeController;import com.jolin.common.dto.PageDTO;import com.jolin.common.dto.ResultDTO;import com.jolin.common.service.CommonSecurityService;import com.jolin.dto.MenuDTO;import com.jolin.service.IMenuService;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import java.util.Map;@RestController@Api(tags = {"04.菜单操作接口"})@ApiSupport(order = 4)@RequestMapping("/sys/menu")public class MenuController extends BaseTreeController<IMenuService, MenuDTO> {    //TODO zhaozhao 不建议提供全量查询接口    @ApiOperation(value = "加载全部菜单，列表平铺形式，获取所有菜单信息")    @GetMapping(value = "/query/all")    public ResultDTO menuList() {        PageDTO pageDTO = new PageDTO();        pageDTO.setPage(1);        pageDTO.setPageSize(1000);        pageDTO = iBaseService.getPage(pageDTO);        return new ResultDTO(pageDTO.getList());    }    @ApiOperation(value = "加载全部菜单，树状结构,只针对vue-router使用，后续考虑删除")    @GetMapping(value = "/query/all/tree")    public ResultDTO menuListForRouter() {        final String userName = CommonSecurityService.instance.getCurrentLoginName();        Map map = iBaseService.findMenuAllForRouter(userName);        return new ResultDTO(map);    }}
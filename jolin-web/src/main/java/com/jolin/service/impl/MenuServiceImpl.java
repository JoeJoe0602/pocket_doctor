package com.jolin.service.impl;

import com.jolin.common.base.BaseTreeServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Menu;
import com.jolin.dto.MenuDTO;
import com.jolin.dto.MenuRouterDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.MenuMapper;
import com.jolin.service.IMenuService;
import com.jolin.service.IRoleMenuService;
import com.jolin.service.IRoleUserService;
import com.jolin.service.IUserInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl extends BaseTreeServiceImpl<MenuMapper, Menu, MenuDTO> implements IMenuService<Menu> {

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;

    @Override
    public CommonCacheUtil getCommonCacheUtil() {
        return commonCacheUtil;
    }

    @Override
    public List<String> findIdsByMenuUrl(String menuUrl) {
        return iBaseRepository.findIdsByMenuUrl(menuUrl);
    }

    @Override
    public PageDTO<MenuDTO> getPage(PageDTO<MenuDTO> pageDTO) {
        Menu menu = getDomainFilterFromPageDTO(pageDTO);
        IPage<Menu> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), menu);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, MenuDTO.class, pageDTO);
    }

    @Override
    public Map findMenuAllForRouter(String loginName) {
        List<MenuRouterDTO> tree = new ArrayList<>();
        Map resultMaprouter = new HashMap();
        //TODO 优化zhaozhao，直接根据用户id查询菜单
        String userId = iUserInfoService.findIdByLoginName(loginName);
        if (StrUtil.isBlank(userId)) {
            throw new SysException(messageSource.getMessage("exception.noUser", null, LocaleContextHolder.getLocale()));
        }
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
        if (CollectionUtil.isEmpty(roleIds)) {
            resultMaprouter.put("router", tree);
            return resultMaprouter;
        }
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOs = findByIds(menuIds);

        List<MenuRouterDTO> menuRouterDTOList = menuListToMenuRouterList(menuDTOs);
        List<MenuRouterDTO> menuTree = this.listToTree(menuRouterDTOList, null);

        resultMaprouter.put("router", menuTree);
        return resultMaprouter;
    }

    @Override
    public MenuDTO beforeCreate(MenuDTO dto) throws BaseException {
        dto = super.beforeCreate(dto);
        if ("external".equals(dto.getMenuClassify())) {
            dto.setParentId(dto.getParentId());
            dto.setMenuUrl(null);
        }
        if (StrUtil.isBlank(dto.getSmallIconPath())) {
            dto.setSmallIconPath("setting");
        }
        return dto;
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        QueryWrapper<Menu> ew = new QueryWrapper<>();
        ew.in("parent_id", ids);
        List<Menu> children = iBaseRepository.selectList(ew);
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException(messageSource.getMessage("exception.hasChild", null, LocaleContextHolder.getLocale()));
        }

        if (CollectionUtil.isNotEmpty(iRoleMenuService.findRoleIdsByMenuIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containRole", null, LocaleContextHolder.getLocale()));
        }
        return true;
    }

    @Override
    public MenuDTO afterUpdate(MenuDTO dto) throws BaseException {
        deleteCacheOfFindIdsByMenuUrl();
        return super.beforeUpdate(dto);
    }

    @Override
    public Boolean afterRemove(String id) {
        deleteCacheOfFindIdsByMenuUrl();
        return super.afterRemove(id);
    }

    @Override
    public Boolean afterBatchRemove(List<String> ids) {
        deleteCacheOfFindIdsByMenuUrl();
        return super.afterBatchRemove(ids);
    }

}

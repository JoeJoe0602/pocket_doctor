package com.jolin.service.impl;

import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Role;
import com.jolin.dto.RoleDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.RoleMapper;
import com.jolin.service.IRoleGroupService;
import com.jolin.service.IRoleMenuService;
import com.jolin.service.IRoleService;
import com.jolin.service.IRoleUserService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDTO> implements IRoleService<Role> {
    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    @Override
    public PageDTO<RoleDTO> getPage(PageDTO<RoleDTO> pageDTO) {
        Role role = getDomainFilterFromPageDTO(pageDTO);
        IPage<Role> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), role);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, RoleDTO.class, pageDTO);
    }

    @Override
    public RoleDTO findByRoleName(String name) {
        Role role = iBaseRepository.findByRoleName(name);
        return domainToDTO(role);
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        if (CollectionUtil.isNotEmpty(iRoleMenuService.findRoleIdsByMenuIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containMenu", null, LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleGroupService.findFirstIdsBySecondIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containGroup", null, LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleUserService.findUserIdsByRoleIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null, LocaleContextHolder.getLocale()));
        }
        return true;
    }
}

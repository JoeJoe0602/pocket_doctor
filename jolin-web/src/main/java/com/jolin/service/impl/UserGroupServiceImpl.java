package com.jolin.service.impl;

import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.UserGroup;
import com.jolin.dto.UserGroupDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.UserGroupMapper;
import com.jolin.service.IRoleGroupService;
import com.jolin.service.IUserGroupService;
import com.jolin.service.IUserGroupUserService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup, UserGroupDTO> implements IUserGroupService<UserGroup> {

    @Autowired
    private IUserGroupUserService iUserGroupUserService;

    @Autowired
    private IRoleGroupService iRoleGroupService;

    @Override
    public PageDTO<UserGroupDTO> getPage(PageDTO<UserGroupDTO> pageDTO) {
        UserGroup userGroup = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserGroup> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userGroup);
        return CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, UserGroupDTO.class, pageDTO);
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        if (CollectionUtil.isNotEmpty(iUserGroupUserService.findUserIdsByGroupIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null, LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(iRoleGroupService.findSecondIdsByFirstIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containRole", null, LocaleContextHolder.getLocale()));
        }

        return true;
    }
}

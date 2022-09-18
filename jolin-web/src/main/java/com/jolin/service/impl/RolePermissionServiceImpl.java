package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.RolePermission;
import com.jolin.dto.RolePermissionDTO;
import com.jolin.mapper.RolePermissionMapper;
import com.jolin.service.IRolePermissionService;
import org.springframework.stereotype.Service;


@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission, RolePermissionDTO> implements IRolePermissionService<RolePermission> {


    @Override
    public PageDTO getPage(PageDTO<RolePermissionDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        RolePermission rolePermission = getDomainFilterFromPageDTO(pageDTO);
        IPage<RolePermission> rolePermissionIPage = iBaseRepository.getPage(page, rolePermission);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(rolePermissionIPage, RolePermissionDTO.class, pageDTO);
        return resultPage;
    }
}

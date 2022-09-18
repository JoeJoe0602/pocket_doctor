package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Permission;
import com.jolin.dto.PermissionDTO;
import com.jolin.mapper.PermissionMapper;
import com.jolin.service.IPermissionService;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission, PermissionDTO> implements IPermissionService<Permission> {


    @Override
    public PageDTO getPage(PageDTO<PermissionDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Permission permission = getDomainFilterFromPageDTO(pageDTO);
        IPage<Permission> permissionIPage = iBaseRepository.getPage(page, permission);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(permissionIPage, PermissionDTO.class, pageDTO);
        return resultPage;
    }
}

package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Role;
import com.jolin.dto.RoleDTO;
import com.jolin.log.dto.WebLogsDTO;
import com.jolin.mapper.RoleMapper;
import com.jolin.service.IRoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDTO> implements IRoleService<Role> {


    @Override
    public PageDTO getPage(PageDTO<RoleDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Role role = getDomainFilterFromPageDTO(pageDTO);
        IPage<Role> roleIPage = iBaseRepository.getPage(page, role);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(roleIPage, RoleDTO.class, pageDTO);
        return resultPage;
    }
}

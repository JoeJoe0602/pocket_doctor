package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.SystemConfig;
import com.jolin.dto.SystemConfigDTO;
import com.jolin.mapper.SystemConfigMapper;
import com.jolin.service.ISystemConfigService;
import org.springframework.stereotype.Service;


@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfigMapper, SystemConfig, SystemConfigDTO> implements ISystemConfigService<SystemConfig> {


    @Override
    public PageDTO getPage(PageDTO<SystemConfigDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        SystemConfig systemConfig = getDomainFilterFromPageDTO(pageDTO);
        IPage<SystemConfig> systemConfigIPage = iBaseRepository.getPage(page, systemConfig);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(systemConfigIPage, SystemConfigDTO.class, pageDTO);
        return resultPage;
    }
}

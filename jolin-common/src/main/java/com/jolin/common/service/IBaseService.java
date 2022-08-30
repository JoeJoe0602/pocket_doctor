package com.jolin.common.service;

import cn.hutool.core.util.IdUtil;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.BaseDTO;

/**
 * 业务表基础service
 */
public interface IBaseService<DTO extends BaseDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {
    @Override
    default DTO beforeCreate(DTO dto) {
        dto = IBaseCommonService.super.beforeCreate(dto);
        dto.setId(IdUtil.simpleUUID());
        // 初始化
        dto.setIsDelete(0);
        return dto;
    }
}

package com.jolin.common.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.BaseDTO;

/**
 * service table basic service
 */
public interface IBaseService<DTO extends BaseDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {
    @Override
    default DTO beforeCreate(DTO dto) {
        dto = IBaseCommonService.super.beforeCreate(dto);
        dto.setId(null);
        // Initialization
        dto.setIsDelete(0);
        return dto;
    }
}

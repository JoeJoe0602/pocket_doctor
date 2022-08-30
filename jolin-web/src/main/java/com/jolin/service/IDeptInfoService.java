package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseTreeService;
import com.jolin.dto.DeptInfoDTO;

import java.util.List;

/**
 * 机构Service
 */
public interface IDeptInfoService<D extends CommonDomain> extends IBaseTreeService<DeptInfoDTO, D> {

    List<DeptInfoDTO> findAll();
}
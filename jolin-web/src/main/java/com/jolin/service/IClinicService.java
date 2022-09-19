package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.IBaseService;
import com.jolin.dto.ClinicDTO;
import com.jolin.dto.DoctorDTO;

/**
 * 用户Service
 */
public interface IClinicService<D extends CommonDomain> extends IBaseService<ClinicDTO, D> {

    PageDTO getPageDistance(PageDTO<ClinicDTO> pageDTO);
}
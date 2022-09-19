package com.jolin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.IBaseService;
import com.jolin.domain.Doctor;
import com.jolin.dto.DoctorDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Service
 */
public interface IDoctorService<D extends CommonDomain> extends IBaseService<DoctorDTO, D> {

    PageDTO getPageDistance(PageDTO<DoctorDTO> pageDTO);
}
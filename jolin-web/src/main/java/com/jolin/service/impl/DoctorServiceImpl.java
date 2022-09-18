package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Doctor;
import com.jolin.dto.DoctorDTO;
import com.jolin.mapper.DoctorMapper;
import com.jolin.service.IDoctorService;
import org.springframework.stereotype.Service;


@Service
public class DoctorServiceImpl extends BaseServiceImpl<DoctorMapper, Doctor, DoctorDTO> implements IDoctorService<Doctor> {


    @Override
    public PageDTO getPage(PageDTO<DoctorDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Doctor doctor = getDomainFilterFromPageDTO(pageDTO);
        IPage<Doctor> doctorIPage = iBaseRepository.getPage(page, doctor);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(doctorIPage, DoctorDTO.class, pageDTO);
        return resultPage;
    }
}

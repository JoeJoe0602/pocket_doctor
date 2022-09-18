package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Appointment;
import com.jolin.domain.HealthRecord;
import com.jolin.dto.AppointmentDTO;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.mapper.AppointmentMapper;
import com.jolin.mapper.HealthRecordMapper;
import com.jolin.service.IAppointmentService;
import com.jolin.service.IHealthRecordService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;


@Service
public class AppointmentServiceImpl extends BaseServiceImpl<AppointmentMapper, Appointment, AppointmentDTO> implements IAppointmentService<Appointment> {


    @Override
    public PageDTO getPage(PageDTO<AppointmentDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Appointment appointment = getDomainFilterFromPageDTO(pageDTO);
        IPage<Appointment> appointmentIPage = iBaseRepository.getPage(page, appointment);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(appointmentIPage, AppointmentDTO.class, pageDTO);
        return resultPage;
    }
}

package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.domain.ChatRecord;
import com.jolin.domain.HealthRecord;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.dto.RoleDTO;

import java.util.List;

/**
 * 用户Service
 */
public interface IHealthRecordService<D extends CommonDomain> extends IBaseService<HealthRecordDTO, D> {

    List<HealthRecord> getHealthRecord(HealthRecord healthRecord);

}
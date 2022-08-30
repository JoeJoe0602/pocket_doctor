package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseJoinService;
import com.jolin.dto.DeptUserJoinDTO;

import java.util.List;

public interface IDeptUserService<D extends CommonDomain> extends IBaseJoinService<DeptUserJoinDTO, D> {

    List<String> findUserIdsByDeptIds(List<String> deptIds);

    List<String> findDeptIdsByUserIds(List<String> userIds);
}

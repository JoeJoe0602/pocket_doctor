package com.jolin.mapper;

import com.jolin.common.base.IBaseTreeMapper;
import com.jolin.domain.DeptInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DeptInfoMapper extends IBaseTreeMapper<DeptInfo> {

    IPage<Map> selectPage(@Param("page") Page page, @Param("deptInfo") DeptInfo deptInfo);

    List<DeptInfo> findAll(@Param("deptInfo") DeptInfo deptInfo);

    DeptInfo findByDeptName(String deptName);

    List<DeptInfo> findAllSort();
}

package com.jolin.mapper;

import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.UserInfo;
import com.jolin.dto.UserInfoDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    IPage<Map> getUserOnlyByRoleIdOrDeptIdPage(@Param("page")Page page, @Param("userInfo") UserInfoDTO userInfo);
    /**
     *  分页
     */
    IPage<UserInfo> selectPage(@Param("page") Page page, @Param("userInfo") UserInfo userInfo);

    String findIdByLoginName(String loginName);

    UserInfo findByLoginName(String loginName);

    List<UserInfo> findUsersByDeptId(String deptId);

    List<UserInfo> findUserByRoleId(String roleId);

    UserInfo findByPhoneNum(String phoneNum);
    UserInfo findByEmail(String email);

    String findIdByPhoneNum(String PhoneNum);

    String findIdByEmail(String email);

    String checkUserExist(String loginName);
}

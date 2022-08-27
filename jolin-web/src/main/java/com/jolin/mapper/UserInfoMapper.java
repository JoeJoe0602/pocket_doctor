package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.UserInfo;
import com.jolin.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    /**
     *  分页
     */
    IPage<UserInfo> selectPage(@Param("page") Page page, @Param("userInfo") UserInfo userInfo);

    String findIdByLoginName(String loginName);

    UserInfo findByLoginName(String loginName);

}

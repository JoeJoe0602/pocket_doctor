package com.jolin.mapper;

import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.UserGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper extends IBaseMapper<UserGroup> {

    /**
     *  分页
     */
    IPage<UserGroup> selectPage(@Param("page") Page page, @Param("userGroup") UserGroup userGroup);

}

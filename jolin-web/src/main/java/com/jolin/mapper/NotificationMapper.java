package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Notification;
import org.apache.ibatis.annotations.Param;

public interface NotificationMapper extends IBaseMapper<Notification> {


    IPage<Notification> getPage(@Param("page") Page page, @Param("notification") Notification  notification);

}

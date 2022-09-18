package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Notification;
import com.jolin.dto.NotificationDTO;
import com.jolin.mapper.NotificationMapper;
import com.jolin.service.INotificationService;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl extends BaseServiceImpl<NotificationMapper, Notification, NotificationDTO> implements INotificationService<Notification> {


    @Override
    public PageDTO getPage(PageDTO<NotificationDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Notification notification = getDomainFilterFromPageDTO(pageDTO);
        IPage<Notification> notificationIPage = iBaseRepository.getPage(page, notification);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(notificationIPage, NotificationDTO.class, pageDTO);
        return resultPage;
    }
}

package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.domain.Notification;
import com.jolin.dto.NotificationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户Service
 */
public interface INotificationService<D extends CommonDomain> extends IBaseService<NotificationDTO, D> {

    List<Notification> getNotification(Notification notification);

}
package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.NotificationDTO;
import com.jolin.service.INotificationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Notification"})
@ApiSort(24)
@RestController
@RequestMapping("sys/notification")
public class NotificationController extends BaseController<INotificationService, NotificationDTO> {




}

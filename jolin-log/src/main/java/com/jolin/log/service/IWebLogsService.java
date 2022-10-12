package com.jolin.log.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.common.util.WebSiteUtil;
import com.jolin.log.annotation.OperationLogAnno;
import com.jolin.log.dto.WebLogsDTO;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;

public interface IWebLogsService<D extends CommonDomain> extends IBaseService<WebLogsDTO, D> {

    default WebLogsDTO beforeSaveLog(JoinPoint joinPoint, Map<String,Object> param) throws ClassNotFoundException, SocketException, UnknownHostException {
        LocalDateTime startTime = (LocalDateTime) param.get("startTime");
        String currentLoginName = (String) param.get("currentLoginName");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {

            // There may be other non-interface type methods in controller that should not be logged
            return null;
        }
        HttpServletRequest request = attributes.getRequest();

        // The ip address of the remote client
        String remoteClientIp = WebSiteUtil.getIpAddress(request);

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        WebLogsDTO webLogsDTO = new WebLogsDTO();
        webLogsDTO.setStartTime(startTime);
        webLogsDTO.setEndTime(LocalDateTime.now());
        webLogsDTO.setLogUserName(currentLoginName);
        webLogsDTO.setLogMethodName(methodName);
        webLogsDTO.setLogClientIp(remoteClientIp);
        webLogsDTO.setLogServerIp(WebSiteUtil.getLocalIP());

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    if (method.getAnnotation(OperationLogAnno.class) != null) {
                        webLogsDTO.setModule(method.getAnnotation(OperationLogAnno.class).module());
                        webLogsDTO.setLogInfo(method.getAnnotation(OperationLogAnno.class).logInfo());
                        webLogsDTO.setLogClassName(method.getAnnotation(OperationLogAnno.class).logClassName());
                        webLogsDTO.setLogOperationType(method.getAnnotation(OperationLogAnno.class).logOperationType());
                    } else {
                        webLogsDTO.setModule(targetName);
                        webLogsDTO.setLogInfo("executed" + targetName + methodName + "'operation");
                        webLogsDTO.setLogClassName(targetName);
                        if (methodName.toLowerCase().contains("list")
                                || methodName.toLowerCase().contains("page")
                                || methodName.toLowerCase().contains("search")
                                || methodName.toLowerCase().contains("tree")) {
                            webLogsDTO.setLogOperationType("query");
                        } else if (methodName.toLowerCase().contains("save")
                                || methodName.toLowerCase().contains("edit")
                                || methodName.toLowerCase().contains("create")
                                || methodName.toLowerCase().contains("update")
                                || methodName.toLowerCase().contains("batchCreate")
                                || methodName.toLowerCase().contains("add")) {
                            webLogsDTO.setLogOperationType("save");
                        } else if (methodName.toLowerCase().contains("delete")) {
                            webLogsDTO.setLogOperationType("logic delete");
                        }
                    }
                }
            }
        }

        return webLogsDTO;
    }

    default WebLogsDTO saveLog(JoinPoint joinPoint, Map<String,Object> param) throws SocketException, UnknownHostException, ClassNotFoundException {
        WebLogsDTO webLogsDTO = beforeSaveLog(joinPoint, param);
        if (webLogsDTO != null) {
            return create(webLogsDTO);
        }
        return null;
    }
}

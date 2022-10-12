package com.jolin.log.monitor;

import cn.hutool.core.map.MapUtil;
import com.jolin.common.service.CommonSecurityService;
import com.jolin.log.annotation.OperationLogAnno;
import com.jolin.log.service.IWebLogsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class WebMonitorLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LocalDateTime startTime;

    @Autowired
    private IWebLogsService iWebLogsService;

    @Value("${base.log.lock:on}")
    public String loglock;

    /**
     * Pre-notification, which is called after the target method has completed, regardless of what the output of the method is. Pre-call trigger - Record start time
     */
//    @Before("bean(*Controller)")
    @Before("within(com.jolin.common.api.BaseController+)")
//    @Before("within(com.jolin.*.web..*) || within(com.jolin.*.api..*)")
    public void beforeAdvice(JoinPoint joinPoint) {
        startTime = LocalDateTime.now();
    }

    /**
     * Post-notification, which is called after the target method is done, doesn't care what the output of the method is
     */
    @After("within(com.jolin.common.api.BaseController+)")
//    @After("within(com.jolin.*.web..*) || within(com.jolin.*.api..*)")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        if (loglock.equals("on")) {
            iWebLogsService.saveLog(joinPoint, MapUtil.builder().put("startTime", startTime)
                    .put("currentLoginName", CommonSecurityService.instance.getCurrentLoginName()).build());
        }
    }

    /**
     * Return notification, called after successful execution of the target method, to obtain the return value of the target method, but not to modify it (modification does not affect the return value of the method）
     *
     * @param jp               Interface to get some information about the join point
     * @param retVal           The target method returns a value that, like jp, is automatically passed in by spring
     * @param operationLogAnno
     */
    @AfterReturning(returning = "retVal", pointcut = "@annotation(operationLogAnno)")
    public void afterReturningAdvice(JoinPoint jp, Object retVal, OperationLogAnno operationLogAnno) {
        logger.info("-------execute" + operationLogAnno.desc() + "finish-------");
    }

    /**
     * Exception notification, invoked after the target method throws an exception
     */
    @AfterThrowing(throwing = "ex", pointcut = "@annotation(operationLogAnno)")
    public void afterThrowingAdvice(JoinPoint joinPoint, OperationLogAnno operationLogAnno, Exception ex) {
        logger.error("execute" + operationLogAnno.desc() + "exception", ex);
    }
}

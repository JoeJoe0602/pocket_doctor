package com.jolin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.util.CommonCacheUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFailureHandler implements AuthenticationFailureHandler {
    protected final Log logger = LogFactory.getLog(getClass());
    private CommonCacheUtil commonCacheUtil;

    private final ApplicationContext context;

    private Integer retryTime;

    private static final String lockedRedisKeyPre = "base:locked";

    public FormLoginFailureHandler(ApplicationContext applicationContext,Integer retryTime) {
        this.context = applicationContext;
        this.retryTime = retryTime;
        this.commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        String username = request.getParameter("username");
        String lockedRedisKey = lockedRedisKeyPre + ":" + username;
        String time = commonCacheUtil.get(lockedRedisKey);
        //剩余登陆次数
        int count = Integer.parseInt(time);
        //已经登陆的次数
        int t = retryTime-count;
        String message = "密码错误";
        if (count<3) {
            resultDTO.setMessage("您已经登陆错误"+t+"次,还剩"+count+"登陆次数");
        }else{
            resultDTO.setMessage("用户名密码错误");
        }
        resultDTO.setCode(401);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);

    }
}
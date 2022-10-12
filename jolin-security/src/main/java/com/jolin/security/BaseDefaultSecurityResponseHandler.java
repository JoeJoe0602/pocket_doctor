package com.jolin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.exception.BaseException;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseDefaultSecurityResponseHandler implements ResponseHandler {
    protected final Log logger = LogFactory.getLog(getClass());


    @SneakyThrows
    @Override
    public void fail(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        Integer code = 500;
        if (e instanceof BaseException) {
            code = ((BaseException) e).getCode();
            resultDTO.setMessage(e.getMessage());
        }
        response.setStatus(500);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        resultDTO.setCode(code);
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }

    @Override
    @SneakyThrows
    public void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO) {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }
}

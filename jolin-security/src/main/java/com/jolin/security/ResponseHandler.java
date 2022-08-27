package com.jolin.security;

import com.jolin.common.dto.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler {

    public void fail(HttpServletRequest request, HttpServletResponse response, Exception e);

    public void success(HttpServletRequest request, HttpServletResponse response, ResultDTO resultDTO);
}

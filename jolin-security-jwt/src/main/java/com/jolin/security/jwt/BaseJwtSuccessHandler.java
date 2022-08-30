package com.jolin.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jolin.common.dto.ResultDTO;
import com.jolin.security.BaseConstants;
import com.jolin.security.jwt.converter.BaseJwtTokenConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/3/22
 * @describe
 */
public class BaseJwtSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        BaseJwtTokenConverter jwtTokenConverter = new BaseJwtTokenConverter();
        String token = jwtTokenConverter.generateToken(authentication);
        ResultDTO<Object> resultDTO = new ResultDTO<>(BaseConstants.TOKEN_PREFIX +token);
        String resultToken = new ObjectMapper().writeValueAsString(resultDTO);
        response.getWriter().write(resultToken);
    }
}

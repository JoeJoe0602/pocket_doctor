package com.jolin.security.qrcode;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.security.BaseSecurityException;
import com.jolin.security.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Scan the QR code on the mobile terminal to log in
 */
public class BaseQrCodeFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(BaseQrCodeFilter.class);

    //TODO improves the restriction policy of calling interfaces from the same IP address, similar to the restriction of Catcha. For example, the same Ip address can only be invoked five times within five minutes

    // Generate the QR code for login. The front-end can dynamically generate the QR code picture using js according to the qrcodeId
    private RequestMatcher idRequestMatcher = new AntPathRequestMatcher("/qrcodeId");
    // The mobile terminal calls this interface after scanning the two-dimensional code, and tells the server to allow the PC to log in
    private RequestMatcher authRequestMatcher = new AntPathRequestMatcher("/qrcodeAuth");
    // The PC makes a polling call to check whether the login is allowed on the mobile phone
    private RequestMatcher tryRequestMatcher = new AntPathRequestMatcher("/qrcodeTryAuth");

    private CommonCacheUtil commonCacheUtil;

    private ResponseHandler responseHandle;

    private Long expiration;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (idRequestMatcher.matches(request)) {
            String qrcodeId = IdUtil.randomUUID();
            commonCacheUtil.set(getQRCodeRedisKey(qrcodeId), "false", expiration);
            responseHandle.success(request, response, new ResultDTO<>(qrcodeId));
            return;
        } else if (authRequestMatcher.matches(request)) {
            String qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId cannot be null"));
            }
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            System.out.println(token);
            commonCacheUtil.set(getQRCodeRedisKey(qrcodeId), token, expiration);
            responseHandle.success(request, response, new ResultDTO<>("The scan succeeds and the login is allowed"));
            return;
        } else if (tryRequestMatcher.matches(request)) {
            String qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId cannot be null"));
            }

            String cache = commonCacheUtil.get(getQRCodeRedisKey(qrcodeId));
            if (StrUtil.isBlank(cache)) {
                responseHandle.success(request, response, new ResultDTO<>("0:Qr code has expired"));
                return;
            } else if ("false".equals(cache)) {
                responseHandle.success(request, response, new ResultDTO<>("1: The login is not allowed on the mobile terminal. Please wait for the confirmation of the mobile terminal or refresh the two-dimensional code"));
                return;
            }

            //TODO temporarily issues the token of the same user on the mobile terminal to the web terminal, and then generates a new token

            String token = request.getParameter("token");
            if (StrUtil.isBlank(token)) {
                token = request.getHeader(HttpHeaders.AUTHORIZATION);
            }

            responseHandle.success(request, response, new ResultDTO("200:"+token));
            return;
        }
        chain.doFilter(request, response);
    }

    public CommonCacheUtil getCommonCacheUtil() {
        return commonCacheUtil;
    }

    public void setCommonCacheUtil(CommonCacheUtil commonCacheUtil) {
        this.commonCacheUtil = commonCacheUtil;
    }

    public ResponseHandler getResponseHandle() {
        return responseHandle;
    }

    public void setResponseHandle(ResponseHandler responseHandle) {
        this.responseHandle = responseHandle;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    /**
     * Login QR code key
     */
    private String getQRCodeRedisKey(String qrcodeId) {
        return "qrcode::" + qrcodeId;
    }
}

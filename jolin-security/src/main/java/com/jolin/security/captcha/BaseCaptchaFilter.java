package com.jolin.security.captcha;

import cn.hutool.json.JSONUtil;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.util.WebSiteUtil;
import com.jolin.security.BaseAuthenticationRequestDTO;
import com.jolin.security.BaseOrRequestMatcher;
import com.jolin.security.ResponseHandler;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Generates and verifies the Filter for the verification code
 */
public class BaseCaptchaFilter extends OncePerRequestFilter {

    //Verification code processor
    private BaseCaptchaHandler baseCaptchaHandler;

    //Generate the verification code matcher
    private RequestMatcher createCaptchaRequestMatcher;

    //Verify the verification code matcher
    private BaseOrRequestMatcher checkOrCaptchaRequestMatcher;

    private ResponseHandler responseHandle;



    public void setCreateCaptchaRequestMatcher(RequestMatcher createCaptchaRequestMatcher) {
        Assert.notNull(createCaptchaRequestMatcher, "createCaptchaRequestMatcher cannot be null");
        this.createCaptchaRequestMatcher = createCaptchaRequestMatcher;
    }

    public void setHandle(ResponseHandler handle) {
        this.responseHandle = handle;
    }

    public void setCheckOrCaptchaRequestMatcher(BaseOrRequestMatcher checkOrCaptchaRequestMatcher) {
        Assert.notNull(checkOrCaptchaRequestMatcher, "checkOrCaptchaRequestMatcher cannot be null");
        this.checkOrCaptchaRequestMatcher = checkOrCaptchaRequestMatcher;
    }

    public void setBaseCaptchaHandler(BaseCaptchaHandler baseCaptchaHandler) {
        Assert.notNull(baseCaptchaHandler, "baseCaptchaHandler cannot be null");
        this.baseCaptchaHandler = baseCaptchaHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (requiresCreateCaptcha(request, response)) {
            //Return verification code
            String ip = WebSiteUtil.getIpAddress(request);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            try {
                ResultDTO dto = baseCaptchaHandler.createCaptcha(ip);
                response.getWriter().write(JSONUtil.toJsonStr(dto));
            } catch (Exception e) {
                responseHandle.fail(request,response,e);
            }
            return;
        } else if (requiresCheckCaptcha(request, response)) {
            BaseAuthenticationRequestDTO authenticationRequest = new BaseAuthenticationRequestDTO(request,
                    "username", "password");

            try {
                baseCaptchaHandler.checkCaptcha(authenticationRequest);
            } catch (Exception e) {
                responseHandle.fail(request,response, e);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    protected boolean requiresCreateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (this.createCaptchaRequestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Did not match request to %s", this.createCaptchaRequestMatcher));
        }
        return false;
    }

    protected boolean requiresCheckCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (this.checkOrCaptchaRequestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Did not match request to %s", this.checkOrCaptchaRequestMatcher));
        }
        return false;
    }
}

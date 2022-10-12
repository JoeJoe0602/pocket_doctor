package com.jolin.security.captcha;

import cn.hutool.core.collection.CollectionUtil;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.security.BaseDefaultSecurityResponseHandler;
import com.jolin.security.BaseOrRequestMatcher;
import com.jolin.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Configure Captcha logic
 */
public class BaseCaptchaConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseCaptchaConfigurer<H>, H> {
    private String captchUrl = "/captcha";

    //Whether to enable verification code logic
    private String captchaEnable = "on";

    //Validity period of the verification code, expressed in seconds
    private Long captchaMaxWaitSecond = 600L;

    //Limit the number of verification code requests per minute for the same IP address
    private Long captchaSameIpLimitPerMinutes = 60L;

    private RequestMatcher createCaptchaRequestMatcher;

    private BaseOrRequestMatcher checkOrCaptchaRequestMatcher;

    private BaseCaptchaHandler baseCaptchaHandler;

    private ResponseHandler responseHandle;

    private final ApplicationContext context;

    /**
     * This extension is written in reference to CsrfConfigurer to inject context through the constructor
     *
     * @see HttpSecurity#csrf()
     */
    public BaseCaptchaConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getCheckCaptchaRequestMatcher();
        getCreateCaptchaRequestMatcher();
        getBaseCaptchaHandler();
        getResponseHandle();
        // Set the built-in matching path, including formLogin default login port, such as /login
        addCheckPointRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    public void configure(H http) throws Exception {
        BaseCaptchaFilter captchaFilter = new BaseCaptchaFilter();
        captchaFilter.setBaseCaptchaHandler(this.baseCaptchaHandler);
        captchaFilter.setCreateCaptchaRequestMatcher(this.createCaptchaRequestMatcher);
        captchaFilter.setHandle(this.responseHandle);
        // Set the default verification code Filter
        captchaFilter.setCheckOrCaptchaRequestMatcher(this.checkOrCaptchaRequestMatcher);

        captchaFilter = postProcess(captchaFilter);

        http.addFilterBefore(captchaFilter, LogoutFilter.class);
    }
    /**
     * Enable and disable the digital verification code
     * @param captchaEnable
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaEnable(String captchaEnable) {
        this.captchaEnable = captchaEnable;
        return this;
    }

    /**
     * The user-defined verification code is implemented logically. After the configuration, do not use other methods
     * @param baseCaptchaHandler
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaHandler(BaseCaptchaHandler baseCaptchaHandler) {
        this.baseCaptchaHandler = baseCaptchaHandler;
        return this;
    }

    /**
     * Validity period of verification code
     * @param captchaMaxWaitSecond
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaMaxWaitSecond(Long captchaMaxWaitSecond) {
        this.captchaMaxWaitSecond = captchaMaxWaitSecond;
        return this;
    }

    /**
     * Setting the policy interface succeeds or fails
     */
    public BaseCaptchaConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    /**
     * Number of times a verification code is obtained per minute
     * @param captchaSameIpLimitPerMinutes
     * @return
     */
    public BaseCaptchaConfigurer<H> captchaSameIpLimitPerMinutes(Long captchaSameIpLimitPerMinutes) {
        this.captchaSameIpLimitPerMinutes = captchaSameIpLimitPerMinutes;
        return this;
    }

    /**
     * User-defined RequestMatcher extension points
     * The interface that needs verification code verification is defined by the specific project
     */
    public BaseCaptchaConfigurer<H> addCheckPointRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addRequestMatcher(requestMatcher);
        }
        return this;
    }

    public BaseCaptchaConfigurer<H> addAllCheckPointRequestMatcher(List<RequestMatcher> requestMatcherList) {
        if (CollectionUtil.isNotEmpty(requestMatcherList)) {
            getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addAllRequestMatcher(requestMatcherList);
        }
        return this;
    }

    //Configure the Matcher that generates the token
    private RequestMatcher getCreateCaptchaRequestMatcher() {
        if (this.createCaptchaRequestMatcher != null) {
            return this.createCaptchaRequestMatcher;
        }
        this.createCaptchaRequestMatcher = new AntPathRequestMatcher(this.captchUrl, "GET");
        return this.createCaptchaRequestMatcher;
    }

    // Configure the Matcher for verifying tokens
    private RequestMatcher getCheckCaptchaRequestMatcher() {
        if (this.checkOrCaptchaRequestMatcher != null) {
            return this.checkOrCaptchaRequestMatcher;
        }
        this.checkOrCaptchaRequestMatcher = new BaseOrRequestMatcher();
        return this.checkOrCaptchaRequestMatcher;
    }

    // Configure the processor
    private BaseCaptchaHandler getBaseCaptchaHandler() {
        if (this.baseCaptchaHandler != null) {
            return this.baseCaptchaHandler;
        }
        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        this.baseCaptchaHandler = new BaseDefaultCaptchaHandler(captchaEnable, captchaMaxWaitSecond, captchaSameIpLimitPerMinutes, commonCacheUtil);
        return this.baseCaptchaHandler;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }
}

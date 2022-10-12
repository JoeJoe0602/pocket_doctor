package com.jolin.security.limit;

import com.jolin.common.util.CommonCacheUtil;
import com.jolin.security.BaseDefaultSecurityResponseHandler;
import com.jolin.security.BaseOrRequestMatcher;
import com.jolin.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * The logic related to authentication interface restrictions was configured
 */
public class BaseRetryLimitConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseRetryLimitConfigurer<H>, H> {
    // Allow the number of login errors, and block accounts and IP addresses for users who log in incorrectly for a certain number of times
    private Integer retryTime = 5;

    //Is locked and cannot restore the interval after login
    private Long lockedRecoverSecond = 43200L;

    private final ApplicationContext context;

    private BaseOrRequestMatcher limitRequestMatcher;

    private ResponseHandler responseHandle;

    /**
     * This extension is written in reference to CsrfConfigurer to inject context through the constructor
     *
     * @see HttpSecurity#csrf()
     */
    public BaseRetryLimitConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getLimitRequestMatcher();
        getResponseHandle();
        //Set the built-in matching path, including the default login port for formLogin, such as /login
        addRetryLimitRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public void configure(H http) throws Exception {
        BaseProtectionLimitFilter filter = new BaseProtectionLimitFilter();
        filter.setLockedRecoverSecond(lockedRecoverSecond);
        filter.setRetryTime(retryTime);
        filter.setResponseHandle(responseHandle);

        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        filter.setCommonCacheUtil(commonCacheUtil);

        filter.setLimitRequestMatcher(this.limitRequestMatcher);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseRetryLimitConfigurer<H> retryTime(Integer retryTime) {
        this.retryTime = retryTime;
        return this;
    }

    public BaseRetryLimitConfigurer<H> lockedRecoverSecond(Long lockedRecoverSecond) {
        this.lockedRecoverSecond = lockedRecoverSecond;
        return this;
    }

    /**
     * Setting the policy interface succeeds or fails
     *
     * @param handle
     */
    public BaseRetryLimitConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    /**
     * User-defined RequestMatcher extension points.
     * Specifies the interface that is invoked for the maximum number of times
     */
    public BaseRetryLimitConfigurer<H> addRetryLimitRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            getLimitRequestMatcher();
            this.limitRequestMatcher.addRequestMatcher(requestMatcher);
        }
        return this;
    }


    private RequestMatcher getLimitRequestMatcher() {
        if (this.limitRequestMatcher != null) {
            return this.limitRequestMatcher;
        }
        this.limitRequestMatcher = new BaseOrRequestMatcher();
        return this.limitRequestMatcher;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }

}

package com.jolin.security.qrcode;

import com.jolin.common.util.CommonCacheUtil;
import com.jolin.security.BaseDefaultSecurityResponseHandler;
import com.jolin.security.ResponseHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * Configure mobile terminal scanning verification code login
 */
public class BaseQrCodeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseQrCodeConfigurer<H>, H> {

    private Long expiration = 120L;

    private final ApplicationContext context;

    private ResponseHandler responseHandle;

    /**
     * This extension is written in reference to CsrfConfigurer to inject context through the constructor
     *
     * @see HttpSecurity#csrf()
     */
    public BaseQrCodeConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void init(H builder) throws Exception {
        super.init(builder);
        getResponseHandle();
    }

    @Override
    public void configure(H http) throws Exception {
        BaseQrCodeFilter filter = new BaseQrCodeFilter();
        filter.setResponseHandle(responseHandle);

        CommonCacheUtil commonCacheUtil = context.getBean("commonCacheUtil", CommonCacheUtil.class);
        filter.setCommonCacheUtil(commonCacheUtil);

        filter.setExpiration(this.expiration);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseQrCodeConfigurer<H> expiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }

    /**
     * Setting the policy interface succeeds or fails
     *
     * @param handle
     */
    public BaseQrCodeConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        }
        this.responseHandle = new BaseDefaultSecurityResponseHandler();
        return this.responseHandle;
    }
}

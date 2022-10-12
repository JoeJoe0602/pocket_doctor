package com.jolin.security.limit;

import cn.hutool.core.util.StrUtil;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.security.BaseAuthenticationRequestDTO;
import com.jolin.security.BaseOrRequestMatcher;
import com.jolin.security.BaseSecurityException;
import com.jolin.security.ResponseHandler;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class BaseProtectionLimitFilter extends OncePerRequestFilter {

    private CommonCacheUtil commonCacheUtil;

    private static final String lockedRedisKeyPre = "base:locked";

    //Allows the number of login errors, and blocks accounts and IP addresses of users who log in incorrectly for a certain number of times
    private Integer retryTime;
    //Is locked and cannot restore the interval after login

    private long lockedRecoverSecond;

    //Verify the verification code matcher
    private BaseOrRequestMatcher limitRequestMatcher;

    private ResponseHandler responseHandle;

    public void setLimitRequestMatcher(BaseOrRequestMatcher limitRequestMatcher) {
        Assert.notNull(limitRequestMatcher, "limitRequestMatcher cannot be null");
        this.limitRequestMatcher = limitRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Boolean isRequiresLimit = requiresLimit(request, response);
        String lockedRedisKey = "";

        if (isRequiresLimit) {
            BaseAuthenticationRequestDTO authenticationRequest = new BaseAuthenticationRequestDTO(request,
                    "username", "password");
            String loginName = authenticationRequest.getUsername();

            lockedRedisKey = lockedRedisKeyPre + ":" + loginName;
            String time = commonCacheUtil.get(lockedRedisKey);
            if (StrUtil.isBlank(time)) {
                //No record, which means no previous false authentication
                time = retryTime + "";
                commonCacheUtil.set(lockedRedisKey, time);
            }

            Integer t = Integer.parseInt(time);
            if (t == 0) {
                commonCacheUtil.set(lockedRedisKey, time, lockedRecoverSecond);
            }
            if (t <= 0) {

                String errorMsg = "The password is incorrect. The account is locked because the maximum number of retries has been reached";
                long hour =  lockedRecoverSecond%3600;
                long minute =  lockedRecoverSecond%60;
                if (hour == 0) {
                    long hours =  lockedRecoverSecond/3600;
                    errorMsg = errorMsg.concat(String.valueOf(hours)).concat("hours");
                }else if (minute==0){
                    long minutes =  lockedRecoverSecond/60;
                    errorMsg = errorMsg.concat(String.valueOf(minutes)).concat("minutes");
                }else{
                    errorMsg = errorMsg.concat(String.valueOf(lockedRecoverSecond)).concat("seconds");
                }
                BaseSecurityException baseSecurityException = new BaseSecurityException(401,errorMsg);
                responseHandle.fail(request,response, baseSecurityException );
                return;
            }
            //Regardless of the login success or failure to carry out the minus one operation
            commonCacheUtil.increment(lockedRedisKey, -1);
        }

        chain.doFilter(request, response);
        //Not this operation
        if (isRequiresLimit&&SecurityContextHolder.getContext().getAuthentication()!=null) {
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                //Determines if you are logged in, and if so, the restriction is lifted
                commonCacheUtil.remove(lockedRedisKey);
            }
        }
    }

    protected boolean requiresLimit(HttpServletRequest request, HttpServletResponse response) {
        if (this.limitRequestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(LogMessage.format("Did not match request to %s", this.limitRequestMatcher));
        }
        return false;
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public long getLockedRecoverSecond() {
        return lockedRecoverSecond;
    }

    public void setLockedRecoverSecond(long lockedRecoverSecond) {
        this.lockedRecoverSecond = lockedRecoverSecond;
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
}

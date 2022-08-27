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

/**
 * Base一些安全防护功能Filter
 * 1. 认证接口登录限制
 * 1. 其他
 */
public class BaseProtectionLimitFilter extends OncePerRequestFilter {

    private CommonCacheUtil commonCacheUtil;

    private static final String lockedRedisKeyPre = "base:locked";

    //允许登录错误次数，对登录错误一定次数的用户进行封锁账号以及 IP 等措施
    private Integer retryTime;
    //被锁定，不允许登录后恢复时间间隔

    private long lockedRecoverSecond;

    //检验验证码matcher
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
                //没有记录，说明之前没有错误认证
                time = retryTime + "";
                commonCacheUtil.set(lockedRedisKey, time);
            }

            Integer t = Integer.parseInt(time);
            if (t == 0) {
                commonCacheUtil.set(lockedRedisKey, time, lockedRecoverSecond);
            }
            if (t <= 0) {

                String errorMsg = "密码错误,已达到最大重试次数,账户锁定";
                long hour =  lockedRecoverSecond%3600;
                long minute =  lockedRecoverSecond%60;
                if (hour == 0) {
                    long hours =  lockedRecoverSecond/3600;
                    errorMsg = errorMsg.concat(String.valueOf(hours)).concat("小时");
                }else if (minute==0){
                    long minutes =  lockedRecoverSecond/60;
                    errorMsg = errorMsg.concat(String.valueOf(minutes)).concat("分钟");
                }else{
                    errorMsg = errorMsg.concat(String.valueOf(lockedRecoverSecond)).concat("秒");
                }
                BaseSecurityException baseSecurityException = new BaseSecurityException(401,errorMsg);
                responseHandle.fail(request,response, baseSecurityException );
                return;
            }
            //不管登陆成功或失败都进行减一操作
            commonCacheUtil.increment(lockedRedisKey, -1);
        }

        chain.doFilter(request, response);
        //没此操作
        if (isRequiresLimit&&SecurityContextHolder.getContext().getAuthentication()!=null) {
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                //判断是否已经登录，如果已经登录，那么限制取消
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

package com.jolin.conf;

import com.jolin.common.dto.CommonUserDTO;
import com.jolin.common.service.CommonSecurityService;
import com.jolin.security.*;
import com.jolin.security.authorization.BaseDynamicAuthorization;
import com.jolin.security.captcha.BaseCaptchaConfigurer;
import com.jolin.security.complexity.BasePasswordComplexity;
import com.jolin.security.jwt.BaseJwtAccessDeniedHandler;
import com.jolin.security.jwt.BaseJwtAuthenticationEntryPoint;
import com.jolin.security.jwt.BaseJwtSuccessHandler;
import com.jolin.security.jwt.configurer.BaseJwtTokenAuthenticationConfigurer;
import com.jolin.security.limit.BaseRetryLimitConfigurer;
import com.jolin.security.service.BaseSecurityServiceImpl;
import com.jolin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 这些配置要封装到 security内部
    @Value("${base.security.jwt.secret:mySecret}")
    private String secret;

    @Value("${base.security.jwt.jwkSetUri}")
    private String jwkSetUri;

    @Value("${base.security.jwt.expiration:60}")
    // 冒号：默认值语法
    //单位：秒，默认配置1星期：3600*24*7=604800
    private Long expiration;

    @Value("${base.security.jwt.refresh:1800}")
    //冒号：默认值语法
    //单位：秒，默认配置1星期：60*30
    private Long refresh;

    //是否启用验证码逻辑
    @Value("${base.security.captcha-enable:on}")
    private String captchaEnable;

    //验证码有效期，单位：秒
    @Value("${base.security.captcha-max-wait-second:600}")
    private long captchaMaxWaitSecond;

    //同一个IP地址，每分钟限制请求多少次验证码
    @Value("${base.security.captcha-same-ip-limit-per-minutes:60}")
    private long captchaSameIpLimitPerMinutes;

    @Value("${base.security.login.retry-time:5}")
    private Integer retryTime = 5;

    //被锁定，不允许登录后恢复时间间隔
    @Value("${base.security.login.locked-recover-second:43200}")
    private long lockedRecoverSecond = 43200;

    @Value("${base.security.login.qrcode.expiration:120}")
    private Long qrCodeExpiration;

    //获取当前用户的工具类
    @Bean
    public CommonSecurityService baseSecurityService() {
        return new BaseSecurityServiceImpl(passwordEncoder(), basePasswordComplexity());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 过滤器的安全拦截器的每一次的要求
        http.authorizeRequests().filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/error").permitAll()
                .antMatchers("/sys/user/send-email-code").permitAll()
                .antMatchers("/sys/user/send-phone-code").permitAll()
                .antMatchers("/sys/user/retrieve-password").permitAll()
                .antMatchers("/sys/user/email-register-code").permitAll()
                .antMatchers("/sys/user/email-register").permitAll()
                .antMatchers("/sys/user/confirm-code").permitAll()
                .antMatchers("/phoneCodeAuth", "/sendSmsCode").permitAll()
                //通过Oauth2登录时绑定用户接口，暂时开启，否则与JwtAuthorizationTokenFilter逻辑冲突（本系统还没有用户所以查不出来）
                .antMatchers("/bindOAuth2User").permitAll()
                .antMatchers("/file/**").permitAll()
                // 支持跨域
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().access("@baseDynamicAccess.dynamicAccess(request,authentication)");
        http.exceptionHandling().accessDeniedHandler(baseJwtAccessDeniedHandler())
                .authenticationEntryPoint(baseJwtAuthenticationEntryPoint());
        //开启oauth登陆
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        // 支持Form表单登录
        http.formLogin()
                .successHandler(baseJwtSuccessHandler())
                .failureHandler(new FormLoginFailureHandler(getApplicationContext(), retryTime));

        // 支持前后端分离刷新和校验token
        http.apply(new BaseJwtTokenAuthenticationConfigurer<>());

        //支持生成校验验证码
        http.apply(new BaseCaptchaConfigurer<>(getApplicationContext()))
                .addCheckPointRequestMatcher(new AntPathRequestMatcher("/ccc")) // 自定义执行验证码校验的url路径
                .captchaEnable(captchaEnable)
                .captchaMaxWaitSecond(captchaMaxWaitSecond)
                .captchaSameIpLimitPerMinutes(captchaSameIpLimitPerMinutes);

        //认证接口限制相关逻辑
        http.apply(new BaseRetryLimitConfigurer<>(getApplicationContext()))
                .retryTime(retryTime)
                .lockedRecoverSecond(lockedRecoverSecond);


        //JWT没有csrf问题，需要禁用
        http.csrf().disable();
        //JWT不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        //前后端分离，开启cors
        http.cors();
        http.oauth2ResourceServer();
        // security 添加安全响应头
        http.headers(headers -> headers
                        //X-Frame-Options
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                        //X-XSS-Protection
                        .xssProtection(xss -> xss.block(true))
                        //Referer-Policy
                        .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN))
                        //X-Content-Type-Options
//                        .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable())
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
                        )
                        .addHeaderWriter(new StaticHeadersWriter("X-Download-Options", "noopen"))
                        .addHeaderWriter(new StaticHeadersWriter("X-Permitted-Cross-Domain-Policies", "master-only"))

        );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决静态资源被拦截的问题
        web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/favicon.ico", "/index.html", "/login.html");

        // TODO 此处忽略的URL过多，可能不太安全,建议根据需求情况适当开启
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html", "/**/*.png"
                , "/**/*.ttf", "/**/*.svg", "/**/*.woff");

        // swagger start
        web.ignoring().antMatchers("/doc.html");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/v2/api-docs");
        web.ignoring().antMatchers("/v2/api-docs-ext");
        web.ignoring().antMatchers("/configuration/ui");
        web.ignoring().antMatchers("/configuration/security");
        // swagger end

        web.ignoring().antMatchers("/sys/**");
        web.ignoring().antMatchers("/api/**");
        web.ignoring().antMatchers("/register");
        web.ignoring().antMatchers("/retrievePassword");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(baseUserDetailService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BasePasswordComplexity basePasswordComplexity() {
        BasePasswordComplexity basePasswordComplexity = new BasePasswordComplexity();
        return basePasswordComplexity;
    }

    @Bean
    public BaseJwtSuccessHandler baseJwtSuccessHandler() {
        return new BaseJwtSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BasePasswordEncoder();
    }

    @Bean
    public BaseJwtAuthenticationEntryPoint baseJwtAuthenticationEntryPoint() {
        return new BaseJwtAuthenticationEntryPoint();
    }

    @Bean
    public BaseJwtAccessDeniedHandler baseJwtAccessDeniedHandler() {
        return new BaseJwtAccessDeniedHandler();
    }

    //解决spring security 5.x无法注入AuthenticationManager的问题
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return authenticationManager;
    }

    //TODO 以下代码要换一种写法
    @Autowired
    private IUserInfoService iUserService;

    @Bean
    public BaseDynamicAuthorization baseDynamicAccess() {
        return new BaseDynamicAuthorization() {
            @Override
            protected Set<String> getRequiredAuthoritySet(HttpServletRequest request) {
                // TODO 每次请求都要调用此方法，因此一定要加缓存，使用的是Spring Cache
                // TODO 现在是绝对匹配，不支持模糊匹配，用户可以在此自定义扩展其他资源与角色的匹配逻辑，如支持 /** 这种模糊匹配
                // TODO menuUrl与requestUrl没有任何关系,所以TaijiAccessDecisionManager的逻辑一直没有生效
                String requestUrl = request.getRequestURI();
                if (requestUrl.startsWith("/")) {
                    requestUrl = requestUrl.substring(1);
                }
                List<String> menuIds= new ArrayList<>();
                return new HashSet<>();
//                List<String> menuIds = iMenuService.findIdsByMenuUrl(requestUrl);
//                if (CollectionUtils.isEmpty(menuIds)) {
//                    return new HashSet<>();
//                } else {
//                    return new HashSet<>(iRoleMenuService.findRoleIdsByMenuIds(menuIds));
//                }
            }
        };
    }

    @Bean
    public UserDetailsService baseUserDetailService() {
        return new BaseUserDetailServiceImpl() {

            @Override
            public CommonUserDTO findCommonUserDTOByLoginName(String loginName) {
                String userId = iUserService.findIdByLoginName(loginName);
                return iUserService.findWithPasswordById(userId);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                String userId = iUserService.findIdByLoginName(loginName);
                List<String> str = new ArrayList<>();
                return str;
            }
        };
    }


}

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO These configurations are encapsulated inside security
    @Value("${base.security.jwt.secret:mySecret}")
    private String secret;

    @Value("${base.security.jwt.jwkSetUri}")
    private String jwkSetUri;

    @Value("${base.security.jwt.expiration:60}")
    // Colon: Default syntax
    // Unit: second. Default value 1 week: 3600 x 24 x 7=604800
    private Long expiration;

    @Value("${base.security.jwt.refresh:1800}")
    // Colon: Default syntax
    // Unit: second. The default value is 1 week: 60 x 30
    private Long refresh;

    // Whether to enable verification code logic
    @Value("${base.security.captcha-enable:on}")
    private String captchaEnable;

    //Validity period of the verification code, expressed in seconds
    @Value("${base.security.captcha-max-wait-second:600}")
    private long captchaMaxWaitSecond;

    //Limit the number of verification code requests per minute for the same IP address
    @Value("${base.security.captcha-same-ip-limit-per-minutes:60}")
    private long captchaSameIpLimitPerMinutes;

    @Value("${base.security.login.retry-time:5}")
    private Integer retryTime = 5;

    // is locked and cannot restore the interval after login
    @Value("${base.security.login.locked-recover-second:43200}")
    private long lockedRecoverSecond = 43200;

    @Value("${base.security.login.qrcode.expiration:120}")
    private Long qrCodeExpiration;

    //Gets the utility class for the current user
    @Bean
    public CommonSecurityService baseSecurityService() {
        return new BaseSecurityServiceImpl(passwordEncoder(), basePasswordComplexity());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Filter the security interceptor every time it is required
        http.authorizeRequests().filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/error").permitAll()
                .antMatchers("/sys/user/send-email-code").permitAll()
                .antMatchers("/sys/user/send-phone-code").permitAll()
                .antMatchers("/sys/user/retrieve-password").permitAll()
                .antMatchers("/sys/user/email-register-code").permitAll()
                .antMatchers("/sys/user/email-register").permitAll()
                .antMatchers("/sys/user/confirm-code").permitAll()
                .antMatchers("/phoneCodeAuth", "/sendSmsCode").permitAll()
                //Through Oauth2 login binding user interface, temporarily open, otherwise conflict with JwtAuthorizationTokenFilter logic (in this system is not so look not to come out)
                .antMatchers("/bindOAuth2User").permitAll()
                .antMatchers("/file/**").permitAll()
                // Support cross-domain
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().access("@baseDynamicAccess.dynamicAccess(request,authentication)");
        http.exceptionHandling().accessDeniedHandler(baseJwtAccessDeniedHandler())
                .authenticationEntryPoint(baseJwtAuthenticationEntryPoint());
        // Enable oauth login
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        // Support Form login
        http.formLogin()
                .successHandler(baseJwtSuccessHandler())
                .failureHandler(new FormLoginFailureHandler(getApplicationContext(), retryTime));

        // Supports separate refresh and token verification at the front and back ends
        http.apply(new BaseJwtTokenAuthenticationConfigurer<>());

        //  Supports generation of verification codes
        http.apply(new BaseCaptchaConfigurer<>(getApplicationContext()))
                .addCheckPointRequestMatcher(new AntPathRequestMatcher("/ccc")) // 自定义执行验证码校验的url路径
                .captchaEnable(captchaEnable)
                .captchaMaxWaitSecond(captchaMaxWaitSecond)
                .captchaSameIpLimitPerMinutes(captchaSameIpLimitPerMinutes);

        // The authentication interface restricts the related logic
        http.apply(new BaseRetryLimitConfigurer<>(getApplicationContext()))
                .retryTime(retryTime)
                .lockedRecoverSecond(lockedRecoverSecond);

        //JWT does not have csrf problems and needs to be disabled
        http.csrf().disable();
        //JWT does not use sessions
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // Separate the front and back ends to enable cors
        http.cors();
        http.oauth2ResourceServer();
        //security Adds a security response header
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
        //Resolve static resource interception problem
        web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/favicon.ico", "/index.html", "/login.html");

        //TODO Too many urls are ignored here, which may be insecure. You are advised to enable this function based on requirements
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

    //Resolve the issue of AuthenticationManager being unable to be injected by spring security 5.x
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return authenticationManager;
    }

    //TODO The following code should be written differently
    @Autowired
    private IUserInfoService iUserService;

    @Bean
    public BaseDynamicAuthorization baseDynamicAccess() {
        return new BaseDynamicAuthorization() {
            @Override
            protected Set<String> getRequiredAuthoritySet(HttpServletRequest request) {

                // TODO calls this method on every request, so it must be cached, using Spring Cache
                // TODO is now absolute matching, does not support fuzzy matching, users can customize the extension of other resources and role matching logic, such as support /** fuzzy matching
                // TODO menuUrl and requestUrl has nothing to do, so has not been in effect TaijiAccessDecisionManager logic
                String requestUrl = request.getRequestURI();
                if (requestUrl.startsWith("/")) {
                    requestUrl = requestUrl.substring(1);
                }
                List<String> menuIds= new ArrayList<>();
                return new HashSet<>();
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

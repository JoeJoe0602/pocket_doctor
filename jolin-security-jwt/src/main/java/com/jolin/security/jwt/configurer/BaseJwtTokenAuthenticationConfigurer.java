package com.jolin.security.jwt.configurer;

import com.jolin.security.jwt.converter.BaseJwtTokenAuthenticationConverter;
import com.jolin.security.jwt.filter.BaseJwtTokenAuthenticationFilter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * Refresh and verify token configurations in a unified manner
 */
public class BaseJwtTokenAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseJwtTokenAuthenticationConfigurer<H>, H> {

    private Converter<String, Authentication> converter = new BaseJwtTokenAuthenticationConverter();

    @Override
    public void configure(H http) throws Exception {
        BaseJwtTokenAuthenticationFilter filter = new BaseJwtTokenAuthenticationFilter();
        filter.setConverter(converter);
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseJwtTokenAuthenticationConfigurer<H> converter(Converter converter) {
        Assert.notNull(converter, "converter cannot be null");
        this.converter = converter;
        return this;
    }
}

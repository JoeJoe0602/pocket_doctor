package com.jolin.security.jwt.converter;

import com.jolin.security.BaseAuthenticationToken;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class BaseJwtTokenAuthenticationConverter implements Converter<String, Authentication> {
    private BaseJwtTokenConverter jwtTokenConverter = new BaseJwtTokenConverter();

    @Override
    public Authentication convert(String bearerToken) {
        JWTClaimsSet jwtClaimsSet = jwtTokenConverter.verifyRS256Token(bearerToken);
        if (jwtClaimsSet == null) {
            return null;
        }
        Set<GrantedAuthority> grantedAuthorities = obtionGrantedAuthorities(jwtClaimsSet);

        Map<String, Object> claims = jwtClaimsSet.getClaims();

        String userName = jwtClaimsSet.getSubject();

        BaseAuthenticationToken authenticationToken = new BaseAuthenticationToken(userName,claims,grantedAuthorities);

        return authenticationToken;
    }

    // Obtain the permission of the user
    private Set<GrantedAuthority> obtionGrantedAuthorities(JWTClaimsSet jwtClaimsSet ) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        String authorities = (String) jwtClaimsSet.getClaim("authorities");
        return authSet;
    }

    public BaseJwtTokenConverter getJwtTokenConverter() {
        return jwtTokenConverter;
    }

    public void setJwtTokenConverter(BaseJwtTokenConverter jwtTokenConverter) {
        this.jwtTokenConverter = jwtTokenConverter;
    }
}

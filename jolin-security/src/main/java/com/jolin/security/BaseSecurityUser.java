package com.jolin.security;

import com.jolin.common.dto.CommonUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * The user object is cached after a successful login. UserDto is added to facilitate obtaining the current login user
 */
public class BaseSecurityUser extends User {
    private CommonUserDTO userInfoDto;

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userInfoDto = userInfoDto;
    }

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userInfoDto = userInfoDto;
    }

    public CommonUserDTO getUserInfoDto() {
        return userInfoDto;
    }
}

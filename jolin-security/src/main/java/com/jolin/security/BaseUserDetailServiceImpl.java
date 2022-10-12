package com.jolin.security;

import cn.hutool.core.collection.CollectionUtil;
import com.jolin.common.dto.CommonUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main purpose of this class is to provide Spring Security with a user authenticated UserDetails.
 * The UserDetails include the user name, password, availability, expiration, and other information.
 */
public abstract class BaseUserDetailServiceImpl implements UserDetailsService {
    public abstract CommonUserDTO findCommonUserDTOByLoginName(String loginName);

    public abstract List<String> findRoleIdsByLoginName(String loginName);

    // Login authentication
    @Override
    public UserDetails loadUserByUsername(String loginName)
            throws UsernameNotFoundException {
        /** Connect database according to login?? User Name Obtain user information */
        CommonUserDTO user = findCommonUserDTOByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException(loginName);
        }
        if (!"1".equals(user.getState())) {
            throw new UsernameNotFoundException("The user is locked");
        }

        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(loginName);

        boolean enables = true; // Whether the available
        boolean accountNonExpired = true; // Whether the out of date
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // Encapsulated into spring security的user
        com.jolin.security.BaseSecurityUser userdetail = new com.jolin.security.BaseSecurityUser(user, user.getLoginName(), user.getPassword(),
                enables, accountNonExpired, credentialsNonExpired,
                accountNonLocked, grantedAuths);
        return userdetail;
    }

    // Obtain the user's permission
    private Set<GrantedAuthority> obtionGrantedAuthorities(String loginName) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        // Obtain the role of the user
        List<String> roleIds = findRoleIdsByLoginName(loginName);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                authSet.add(new SimpleGrantedAuthority(roleId));
            });
        }
        return authSet;
    }
}

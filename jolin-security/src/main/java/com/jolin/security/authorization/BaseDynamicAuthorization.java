package com.jolin.security.authorization;

import cn.hutool.core.collection.CollectionUtil;
import com.jolin.common.dto.CommonUserDTO;
import com.jolin.security.BaseSecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Security Dynamic authentication implementation class
 */
public abstract class BaseDynamicAuthorization {
    /**
     * Whether it is a whitelist mechanism. If a request is not configured with permission, determine whether it is passed or forbidden.
     * If yes, it is the whitelist mechanism; otherwise, it is the blacklist mechanism
     */
    @Value("${base.security.access.isWhiteLogic:true}")
    public Boolean isWhiteLogic;

    /**
     * @param request  Current request
     * @param authentication Authentication object
     * @return Current user (authentication) Whether the user has permission to access this resource (request)
     */
    public Boolean dynamicAccess(HttpServletRequest request, Authentication authentication) {
        // Check whether the current user is authenticated
        if (!isAuthenticated(authentication)) {
            return false;
        }

        //Check whether the user is locked
        if (authentication.getPrincipal() instanceof BaseSecurityUser) {
            CommonUserDTO userInfoDto = ((BaseSecurityUser) authentication.getPrincipal()).getUserInfoDto();
            if (userInfoDto != null && "0".equals(userInfoDto.getState())) {
                throw new AccessDeniedException("The user is locked.");
            }
        }

        Set<String> requiredAuthoritySet = getRequiredAuthoritySet(request);
        if (CollectionUtil.isEmpty(requiredAuthoritySet)) {
            //Whitelist mechanism or Default blacklist mechanism
            //In the whitelist mechanism, if the permission list is null, the user has the permission by default and returns true
            return isWhiteLogic;
        }

        //The Role set corresponding to the current user is obtained
        Set<String> userAuthoritySet = getUserAuthoritySet(authentication);

        //If requiredAuthoritySet and userAuthoritySet overlap, the user is considered to have the permission to access the resource
        return CollectionUtil.isNotEmpty(CollectionUtil.intersection(requiredAuthoritySet, userAuthoritySet));
    }

    /**
     * Determine whether the current user is authenticated
     */
    protected Boolean isAuthenticated(Authentication authentication) {
        // The current user is displayed
        Object principal = authentication.getPrincipal();
        return principal != null && !"anonymousUser".equals(principal);
    }

    /**
     * Gets the set of permissions (string) that have the eligibility to access this resource (request)
     */
    protected abstract Set<String> getRequiredAuthoritySet(HttpServletRequest request);

    /**
     * Gets the set of permissions (string) owned by the current user (authentication)
     */
    protected Set<String> getUserAuthoritySet(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return roles;
    }

}

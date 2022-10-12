package com.jolin.common.service;

import java.util.Set;

/**
 * spring security utility classes to get the current logged-in user, etc
 */
public abstract class CommonSecurityService {
    public static CommonSecurityService instance;

    public CommonSecurityService() {
        instance = this;
    }

    /**
     * Obtain the LoginName of the current login user
     */
    public abstract String getCurrentLoginName();

    /**
     * Gets the permission collection of the currently logged in user
     */
    public abstract Set<String> getCurrentAuthorities();

    /**
     *Encrypted password
     *
     * @param rawPassword The original password is not encrypted
     * @return Encrypted string
     */
    public abstract String encodePassword(String rawPassword);
    public abstract Boolean match(String rawPassword,String password);


}

package com.jolin.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


/** 
 *  
 * @EnableGlobalMethodSecurity(securedEnabled=true)
 * Enable the @Secured annotation filtering permission
 *  
 * @EnableGlobalMethodSecurity(prePostEnabled=true)
 * Four annotations are available using the expression time method level of security
 * -@PreAuthorize Limits access to methods based on the expression's evaluation before the method is called
 * -@PostAuthorize allows method calls, but throws a security exception if the expression evaluates to false
 * -@PostFilter allows method calls, but the result of the method must be filtered by expression
 * -@PreFilter allows method calls, but must filter input values before entering the method
 * 
 */  
@Configuration  
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) 
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
  
}  

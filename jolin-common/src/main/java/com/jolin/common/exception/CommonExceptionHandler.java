package com.jolin.common.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handling classes used by the framework itself
 */
@RestControllerAdvice(basePackages = {"com.jolin"})
@ConditionalOnClass(javax.servlet.Servlet.class)
@Order(Ordered.LOWEST_PRECEDENCE)
final class CommonExceptionHandler extends BaseExceptionHandler {

}



package com.jolin.security.complexity;

/**
 * @author jolin
 * @version 1.0
 * @date 2021/5/11
 * @describe
 */
public class BasePasswordPattern {
    private String pattern;
    private String message;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

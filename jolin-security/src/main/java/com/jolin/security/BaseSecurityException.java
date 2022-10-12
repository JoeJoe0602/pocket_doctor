package com.jolin.security;

import com.jolin.common.exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseSecurityException extends BaseException {
    private static final Long serialVersionUID = 1L;
    // Code is an abnormal status code. Users should pay attention to the fact that the distribution of the status code is not repeated (confusion may occur after repeated).
    // You are advised to add two digits to the http status code for definition
    private Integer code = 40004;

    public BaseSecurityException(Integer code, String template, Object... params) {
        super(template, params);
        this.code = code;
    }


    public BaseSecurityException(String template, Object... params) {
        super(template, params);
    }

    public BaseSecurityException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
package com.jolin.exception;


import com.jolin.common.exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Service Exception Class
 */
@Data
@NoArgsConstructor
public class SysException extends BaseException {
    private static final Long serialVersionUID = 1L;

    // code is the abnormal status code. Users should pay attention to the fact that the status code is not assigned repeatedly (confusion may occur after repetition).
    // You are advised to use the two-digit format after the http status code for definition
    private static final Integer code = 40001;

    public SysException(String template, Object... params) {
        super(template, params);
    }

    public SysException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}

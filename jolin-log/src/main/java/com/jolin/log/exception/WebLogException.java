package com.jolin.log.exception;

import com.jolin.common.exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebLogException extends BaseException {
    private static final Long serialVersionUID = 1L;
    // code is the abnormal status code. Users should pay attention to the fact that the status code is not assigned repeatedly (confusion may occur after repetition).
    // You are advised to use the two-digit format after the http status code for definition
    private static final Integer code = 42001;

    public WebLogException(String template, Object... params) {
        super(template, params);
    }

    public WebLogException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
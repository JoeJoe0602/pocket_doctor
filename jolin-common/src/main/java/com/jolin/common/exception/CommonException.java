package com.jolin.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonException extends BaseException {
    private static final Long serialVersionUID = 1L;

    /**
     *
     * code is an abnormal status code. Users should pay attention to the fact that the distribution of the status code is not repeated (confusion may occur after repeated).
     * You are advised to add two digits to the http status code for definition
     */
    private static final Integer CODE = 40003;

    public CommonException(String template, Object... params) {
        super(template, params);
    }

    public CommonException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return CODE;
    }
}

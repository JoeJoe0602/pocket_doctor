package com.jolin.security.sms;

import com.jolin.common.exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsSecurityException extends BaseException {
    private static final Long serialVersionUID = 1L;
    // code为异常状态码，用户需要注意状态码的分配不重复（重复后容易产生混淆）
    // 建议使用http状态码后面补充两位数字的格式进行定义
    private   Integer code = 40005;

    public SmsSecurityException(Integer code ,String template, Object... params) {
        super(template, params);
        this.code = code;
    }



    public SmsSecurityException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
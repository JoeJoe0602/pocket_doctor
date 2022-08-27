package com.jolin.security.captcha;

import com.jolin.common.dto.ResultDTO;
import com.jolin.security.BaseAuthenticationRequestDTO;

public interface BaseCaptchaHandler {

    ResultDTO createCaptcha(String clientIp);

    Boolean checkCaptcha(BaseAuthenticationRequestDTO authenticationRequest);
}

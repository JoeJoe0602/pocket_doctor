package com.jolin.common.sms;

import java.util.Map;

/**
 * @author jolin
 * @version 1.0
 * @date 2021/4/6
 * @describe
 */
public interface SmsThirdSendService {

     Boolean send(Map<String,Object> data);
}

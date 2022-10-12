package com.jolin.common.sms;

import java.util.Map;

public interface SmsThirdSendService {

     Boolean send(Map<String,Object> data);
}

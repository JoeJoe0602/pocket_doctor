package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class LogDTO extends BaseDTO {

    private  String info;

    private  String logClassName;

    private  String logClientIp;

    private  String logInfo;

    private  String logMethodName;

    private  String logOperationType;

    private String  logPrimaryKey;

    private String logServerIp;

    private String logTableName;

    private  String logUrl;

    private  String logUserName;

    private  String module;

    private  String type;

    private  Long  startTime;

    private  Long  endTime;

}

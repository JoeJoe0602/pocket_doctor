package com.jolin.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jolin.common.dto.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WebLogsDTO extends BaseDTO {
    private static final long serialVersionUID = 8654485582322279564L;
    /**
     * Entity
     */
    private String logClassName;
    /**
     * Method description
     */
    private String logInfo;
    /**
     *  Method name
     */
    private String logMethodName;
    /**
     * Operational method
     */
    private String logOperationType;
    /**
     *
     */
    private String logPrimaryKey;
    /**
     * Log table name
     */
    private String logTableName;
    /**
     * Log url
     */
    private String logUrl;
    /**
     * Log user
     */
    private String logUserName;
    /**
     * Module name
     */
    private String module;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private LocalDateTime endTime;

    private String logServerIp;
    private String logClientIp;
    /**
     * The following two are reserved fields
     */
    private String info;
    private String type;


}
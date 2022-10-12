package com.jolin.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CaffeineCacheDTO implements Serializable {
    private static final long serialVersionUID = 11L;


    //Cache expiration time, in seconds
    private Long expireSecond;

    //Cache data
    private String value;

}

package com.jolin.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * The unified interface returns an object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 2783377098145240357L;
    private Integer code = HttpStatus.OK.value();
    private String message = "success";
    private T data;

    public ResultDTO(T data) {
        this.data = data;
    }

    public ResultDTO(Integer code) {
        this.code = code;
    }

    public ResultDTO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
}

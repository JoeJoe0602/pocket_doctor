package com.jolin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author chenzhe
 * @version 1.0
 * @date 2021/8/25
 * @describe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    @NotBlank(message = "from不能为空")
    private String from;
    @NotBlank(message = "alias不能为空")
    private String alias;
    @NotBlank(message = "to不能为空")
    private String to;
    private String text;
    @NotBlank(message = "subject不能为空")
    private String subject;
}

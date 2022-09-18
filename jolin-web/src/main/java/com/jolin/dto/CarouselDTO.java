package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CarouselDTO extends BaseDTO {

    private  String name;

    private  String photoUrl;

    private  String link;
}

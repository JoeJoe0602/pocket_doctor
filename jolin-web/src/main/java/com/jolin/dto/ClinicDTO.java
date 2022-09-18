package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class ClinicDTO extends BaseDTO {


    private  String name;

    private  String photo;

    private  String description;

    private  String location;

    private  String latitude;

    private  String longitude;
}

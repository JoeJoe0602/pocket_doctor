package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class DoctorDTO extends BaseDTO {

    private  String userId;

    private  String clinicId;

    private  String name;

    private  String photo;

    private  String description;

    private  String latitude;

    private  String longitude;

}

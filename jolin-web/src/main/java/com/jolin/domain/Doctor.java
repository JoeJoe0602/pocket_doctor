package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Doctor extends BaseDomain {


    private  String userId;

    private  String clinicId;

    private  String name;

    private  String photo;

    private  String description;

    private  String latitude;

    private  String longitude;




}

package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Clinic extends BaseDomain {

    private  String name;

    private  String photo;

    private  String description;

    private  String location;

    private  String latitude;

    private  String longitude;



}

package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("clinic")
public class Clinic extends BaseDomain {

    private  String name;

    private  String photo;

    private  String description;

    private  String location;

    private  String latitude;

    private  String longitude;



}

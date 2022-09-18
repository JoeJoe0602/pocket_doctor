package com.jolin.domain;

import com.jolin.common.base.BaseDomain;
import lombok.Data;

@Data
public class Article extends BaseDomain {

    private  String categoryId;

    private  String title;

    private  String content;

    private  String photoUrl;

    private  Integer readNum;

    private  Integer likedNum;

    private  Integer collectNum;



}

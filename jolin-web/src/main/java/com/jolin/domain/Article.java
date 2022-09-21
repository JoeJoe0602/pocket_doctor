package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article extends BaseDomain {

    private  String categoryId;

    private  String title;

    private  String content;

    private  String photoUrl;

    private  Integer readNum;

    private  Integer likedNum;

    private  Integer collectNum;



}

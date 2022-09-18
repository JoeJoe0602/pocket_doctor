package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class ArticleDTO extends BaseDTO {

    private  String categoryId;

    private  String title;

    private  String content;

    private  String photoUrl;

    private  Integer readNum;

    private  Integer likedNum;

    private  Integer collectNum;
}

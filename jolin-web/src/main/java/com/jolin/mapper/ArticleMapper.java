package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Article;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends IBaseMapper<Article> {


    IPage<Article> getPage(@Param("page") Page page, @Param("article") Article  article);

}

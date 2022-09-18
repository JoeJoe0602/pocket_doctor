package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Article;
import com.jolin.dto.ArticleDTO;
import com.jolin.mapper.ArticleMapper;
import com.jolin.service.IArticleService;
import org.springframework.stereotype.Service;


@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article, ArticleDTO> implements IArticleService<Article> {


    @Override
    public PageDTO getPage(PageDTO<ArticleDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Article article = getDomainFilterFromPageDTO(pageDTO);
        IPage<Article> articleIPage = iBaseRepository.getPage(page, article);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(articleIPage, ArticleDTO.class, pageDTO);
        return resultPage;
    }
}

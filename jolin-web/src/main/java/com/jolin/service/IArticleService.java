package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.service.IBaseService;
import com.jolin.dto.ArticleDTO;

/**
 * 用户Service
 */
public interface IArticleService<D extends CommonDomain> extends IBaseService<ArticleDTO, D> {


}
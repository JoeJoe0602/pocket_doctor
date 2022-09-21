package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.ArticleDTO;
import com.jolin.service.IArticleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"✅Article"})
@ApiSort(11)
@RestController
@RequestMapping("sys/article")
public class ArticleController extends BaseController<IArticleService, ArticleDTO> {




}

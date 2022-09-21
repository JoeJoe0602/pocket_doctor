package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;
import com.jolin.dto.CommentDTO;
import com.jolin.service.ICommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Comment"})
@ApiSort(18)
@RestController
@RequestMapping("sys/comment")
public class CommentController extends BaseController<ICommentService, CommentDTO> {




}

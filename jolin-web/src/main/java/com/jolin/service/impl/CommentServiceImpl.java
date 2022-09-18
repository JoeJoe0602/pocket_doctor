package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Comment;
import com.jolin.dto.CommentDTO;
import com.jolin.mapper.CommentMapper;
import com.jolin.service.ICommentService;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment, CommentDTO> implements ICommentService<Comment> {


    @Override
    public PageDTO getPage(PageDTO<CommentDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Comment comment = getDomainFilterFromPageDTO(pageDTO);
        IPage<Comment> commentIPage = iBaseRepository.getPage(page, comment);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(commentIPage, CommentDTO.class, pageDTO);
        return resultPage;
    }
}

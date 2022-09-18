package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends IBaseMapper<Comment> {


    IPage<Comment> getPage(@Param("page") Page page, @Param("comment") Comment  comment);

}

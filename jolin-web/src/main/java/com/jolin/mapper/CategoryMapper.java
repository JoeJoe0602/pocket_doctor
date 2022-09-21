package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends IBaseMapper<Category> {

    IPage<Category> getPage(@Param("page") Page page, @Param("category") Category  categorymodel);

}

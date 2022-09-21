package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Carousel;
import com.jolin.domain.Cases;
import org.apache.ibatis.annotations.Param;

public interface CarouselMapper extends IBaseMapper<Carousel> {

    IPage<Carousel> getPage(@Param("page") Page page, @Param("carousel") Carousel  carouselmodel);

}

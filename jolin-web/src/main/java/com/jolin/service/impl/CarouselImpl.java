package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Carousel;
import com.jolin.domain.Case;
import com.jolin.dto.CarouselDTO;
import com.jolin.dto.CaseDTO;
import com.jolin.mapper.CarouselMapper;
import com.jolin.mapper.CaseMapper;
import com.jolin.service.ICarouselService;
import com.jolin.service.ICaseService;
import org.springframework.stereotype.Service;


@Service
public class CarouselImpl extends BaseServiceImpl<CarouselMapper, Carousel, CarouselDTO> implements ICarouselService<Carousel> {


    @Override
    public PageDTO getPage(PageDTO<CarouselDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Carousel carouselmodel = getDomainFilterFromPageDTO(pageDTO);
        IPage<Carousel> carouselIPage = iBaseRepository.getPage(page,carouselmodel);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(carouselIPage, CarouselDTO.class, pageDTO);
        return resultPage;
    }
}

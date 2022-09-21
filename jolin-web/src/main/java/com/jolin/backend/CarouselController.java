package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.api.BaseController;

import com.jolin.dto.CarouselDTO;
import com.jolin.service.ICarouselService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Carousel"})
@ApiSort(12)
@RestController
@RequestMapping("sys/carousel")
public class CarouselController extends BaseController<ICarouselService, CarouselDTO> {




}

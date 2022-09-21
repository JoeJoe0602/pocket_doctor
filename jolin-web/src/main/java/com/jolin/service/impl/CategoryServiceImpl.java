package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Category;
import com.jolin.dto.CategoryDTO;
import com.jolin.mapper.CategoryMapper;
import com.jolin.service.ICategoryService;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category, CategoryDTO> implements ICategoryService<Category> {


    @Override
    public PageDTO getPage(PageDTO<CategoryDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Category category = getDomainFilterFromPageDTO(pageDTO);
        IPage<Category> caseIPage = iBaseRepository.getPage(page, category);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(caseIPage, CategoryDTO.class, pageDTO);
        return resultPage;
    }
}

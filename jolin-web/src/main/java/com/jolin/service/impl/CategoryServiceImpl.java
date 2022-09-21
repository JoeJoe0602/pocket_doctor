package com.jolin.service.impl;

import com.jolin.common.base.BaseServiceImpl;
import com.jolin.domain.Category;
import com.jolin.dto.CategoryDTO;
import com.jolin.mapper.CategoryMapper;
import com.jolin.service.ICategoryService;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category, CategoryDTO> implements ICategoryService<Category> {


}

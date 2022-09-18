package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.DictionaryType;
import org.apache.ibatis.annotations.Param;

public interface DictionaryTypeMapper extends IBaseMapper<DictionaryType> {


    IPage<DictionaryType> getPage(@Param("page") Page page, @Param("dictionary_type") DictionaryType  dictionaryType);

}

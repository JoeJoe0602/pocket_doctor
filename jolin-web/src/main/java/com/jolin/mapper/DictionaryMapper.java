package com.jolin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.IBaseMapper;
import com.jolin.domain.Dictionary;
import org.apache.ibatis.annotations.Param;

public interface DictionaryMapper extends IBaseMapper<Dictionary> {


    IPage<Dictionary> getPage(@Param("page") Page page, @Param("dictionary") Dictionary  dictionary);

}

package com.jolin.service.impl;

import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.DataItem;
import com.jolin.domain.DataItemType;
import com.jolin.dto.DataItemDTO;
import com.jolin.dto.DataItemTypeDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.DataItemTypeMapper;
import com.jolin.service.IDataItemService;
import com.jolin.service.IDataItemTypeService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DataItemTypeServiceImpl extends BaseServiceImpl<DataItemTypeMapper, DataItemType, DataItemTypeDTO> implements IDataItemTypeService<DataItemType> {

    @Autowired
    private IDataItemService<DataItem> iDataItemService;
    @Override
    public PageDTO<DataItemTypeDTO> getPage(PageDTO<DataItemTypeDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItemType dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<DataItemType> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemTypeDTO.class,pageDTO);
        return resultPage;
    }

    @Override
    public Boolean beforeRemove(String id) {
        super.beforeRemove(id);
        List<DataItemDTO> byDataItemTypeId = iDataItemService.findByDataItemTypeId(Arrays.asList(id));
        if (CollectionUtil.isNotEmpty(byDataItemTypeId)) {
            throw new SysException("字典类型正被使用");
        }
        return true;
    }
}

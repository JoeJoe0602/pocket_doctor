package com.jolin.service.impl;

import com.jolin.common.base.BaseTreeServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.DataItem;
import com.jolin.dto.DataItemDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.DataItemMapper;
import com.jolin.service.IDataItemService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataItemServiceImpl extends BaseTreeServiceImpl<DataItemMapper, DataItem, DataItemDTO> implements IDataItemService<DataItem> {
    @Override
    public PageDTO<DataItemDTO> getPage(PageDTO<DataItemDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemDTO.class, pageDTO);
        return resultPage;
    }

    @Override
    public PageDTO<DataItemDTO> findDataItemByTypePageable(PageDTO<DataItemDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem dataItem = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> dataItemIPage = iBaseRepository.selectPage(page, dataItem);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemDTO.class, pageDTO);
        return resultPage;
    }

    @Override
    public List<DataItemDTO> findByDataItemTypeId(List<String> dataItemTypeIds) {
        List<DataItem> dataItems = iBaseRepository.findByDataItemTypeId(dataItemTypeIds);
        List<DataItemDTO> dataItemDTOS = domainListToDTOList(dataItems);
        return dataItemDTOS;
    }

    @Override
    public PageDTO<DataItemDTO> page(PageDTO<DataItemDTO> pageDTO) {
        DataItem item = getDomainFilterFromPageDTO(pageDTO);
        String name = item.getName();
        if (StrUtil.isNotBlank(name)) {
            PageDTO dto = haveFilter(pageDTO);
            return dto;
        } else {
            PageDTO dto = noFilter(pageDTO);
            return dto;
        }

    }

    @Override
    public List<DataItemDTO> findByPathAndName(DataItemDTO dataItemDTO) {
        String id = dataItemDTO.getParentId();
        DataItemDTO byId = findById(id);
        dataItemDTO.setPath(byId.getPath());
        String[] parentPaths = byId.getPath().split(",");
        List<DataItem> byPathAndName = iBaseRepository.findByPathAndName(dataItemDTO);
        List<String> ids = new ArrayList<>();
        for (DataItem dataItem : byPathAndName) {
            String[] childrenPaths = dataItem.getPath().split(",");
            if (parentPaths.length == childrenPaths.length) {
                continue;
            }
            ids.add(childrenPaths[parentPaths.length]);
        }
        if (CollectionUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<Map> allByIdIn = iBaseRepository.findAllByIdIn(ids);
        List<DataItemDTO> dataItemDTOS = CommonMybatisPageUtil.getInstance().mapListToDTOList(allByIdIn, DataItemDTO.class);
        return dataItemDTOS;

    }

    @Override
    public List<DataItemDTO> findDataItemByDataItemTypeName(String dataItemTypeName) {
        if (StrUtil.isBlank(dataItemTypeName)) {
            throw new SysException("数据字典类型名称不能为空");
        }
        List<Map> dataItemByDataItemTypeName = iBaseRepository.findDataItemByDataItemTypeName(dataItemTypeName);
        List<DataItemDTO> dataItemDTOS = CommonMybatisPageUtil.getInstance().mapListToDTOList(dataItemByDataItemTypeName, DataItemDTO.class);
        return dataItemDTOS;
    }

    /**
     * 如果没有Name过滤条件,查询所有父类分页,之后构造树.
     */
    private PageDTO noFilter(PageDTO<DataItemDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem item = getDomainFilterFromPageDTO(pageDTO);
        item.setParentId("");
        IPage<Map> mapIPage = iBaseRepository.selectPage(page, item);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(mapIPage, DataItemDTO.class, pageDTO);
        return resultPage;
    }

    private PageDTO haveFilter(PageDTO<DataItemDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DataItem item = getDomainFilterFromPageDTO(pageDTO);
        List<DataItem> items = iBaseRepository.findByNameAndDataItemTypeId(item);
        //所有符合条件的id的集合
        List<String> allIds = new LinkedList<>();
        //祖宗类id集合
        List<String> ids = new LinkedList<>();
        for (DataItem dataItem : items) {
            String path = dataItem.getPath();
            String[] split = path.split(",");
            allIds.addAll(Arrays.asList(split));
            ids.add(split[0]);
        }
        //如果没有ids直接返回
        if (CollectionUtil.isEmpty(ids)) {
            pageDTO.setList(new ArrayList());
            return pageDTO;
        }

        IPage<Map> dataItemIPage = iBaseRepository.pageByIds(page, ids);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dataItemIPage, DataItemDTO.class, pageDTO);
        return resultPage;
    }

}

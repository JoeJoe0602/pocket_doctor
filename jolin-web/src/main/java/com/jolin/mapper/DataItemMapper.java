package com.jolin.mapper;

import com.jolin.common.base.IBaseTreeMapper;
import com.jolin.domain.DataItem;
import com.jolin.dto.DataItemDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataItemMapper extends IBaseTreeMapper<DataItem> {

    /**
     * 分页
     */
    IPage<Map> selectPage(@Param("page") Page page, @Param("dataItem") DataItem dataItem);

    List<DataItem> findByDataItemTypeId(@Param("dataItemTypeIds") List<String> dataItemTypeIds);

    List<DataItem> findByNameAndDataItemTypeId(@Param("dataItem") DataItem dataItem);

    IPage<Map> pageByIds(@Param("page") Page page,@Param("ids") List<String> ids);

    List<DataItem> findByPathAndName(@Param("dataItem") DataItemDTO dataItem);

    List<Map> findAllByIdIn(@Param("ids")List<String> ids);

    List<Map> findDataItemByDataItemTypeName(String dataItemTypeName);
}

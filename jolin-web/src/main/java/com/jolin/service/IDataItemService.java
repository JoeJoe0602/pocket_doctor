package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.IBaseTreeService;
import com.jolin.dto.DataItemDTO;

import java.util.List;

/**
 * 数据字典Service
 */
public interface IDataItemService<D extends CommonDomain> extends IBaseTreeService<DataItemDTO, D> {
	
    PageDTO<DataItemDTO> findDataItemByTypePageable(final PageDTO<DataItemDTO> pageDTO);

    List<DataItemDTO> findByDataItemTypeId(List<String> dataItemTypeIds);

    PageDTO<DataItemDTO>  page(PageDTO<DataItemDTO> pageDTO);

    List<DataItemDTO> findByPathAndName(DataItemDTO dataItemDTO);

    List<DataItemDTO> findDataItemByDataItemTypeName(String dataItemTypeName);
}

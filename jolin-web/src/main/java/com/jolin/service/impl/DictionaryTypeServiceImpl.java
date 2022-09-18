package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.DictionaryType;
import com.jolin.dto.DictionaryTypeDTO;
import com.jolin.mapper.DictionaryTypeMapper;
import com.jolin.service.IDictionaryTypeService;
import org.springframework.stereotype.Service;


@Service
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryTypeMapper, DictionaryType, DictionaryTypeDTO> implements IDictionaryTypeService<DictionaryType> {


    @Override
    public PageDTO getPage(PageDTO<DictionaryTypeDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DictionaryType dictionaryType = getDomainFilterFromPageDTO(pageDTO);
        IPage<DictionaryType> appointmentIPage = iBaseRepository.getPage(page, dictionaryType);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(appointmentIPage, DictionaryTypeDTO.class, pageDTO);
        return resultPage;
    }
}

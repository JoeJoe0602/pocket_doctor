package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Dictionary;
import com.jolin.dto.DictionaryDTO;
import com.jolin.mapper.DictionaryMapper;
import com.jolin.service.IDictionaryService;
import org.springframework.stereotype.Service;


@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary, DictionaryDTO> implements IDictionaryService<Dictionary> {


    @Override
    public PageDTO getPage(PageDTO<DictionaryDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Dictionary dictionary = getDomainFilterFromPageDTO(pageDTO);
        IPage<Dictionary> dictionaryIPage = iBaseRepository.getPage(page, dictionary);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(dictionaryIPage, DictionaryDTO.class, pageDTO);
        return resultPage;
    }
}

package com.jolin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.domain.Collect;
import com.jolin.dto.CollectDTO;
import com.jolin.mapper.CollectMapper;
import com.jolin.service.ICollectService;
import org.springframework.stereotype.Service;


@Service
public class CollectServiceImpl extends BaseServiceImpl<CollectMapper, Collect, CollectDTO> implements ICollectService<Collect> {


    @Override
    public PageDTO getPage(PageDTO<CollectDTO> pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Collect collect = getDomainFilterFromPageDTO(pageDTO);
        IPage<Collect> collectIPage = iBaseRepository.getPage(page, collect);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(collectIPage, CollectDTO.class, pageDTO);
        return resultPage;
    }
}

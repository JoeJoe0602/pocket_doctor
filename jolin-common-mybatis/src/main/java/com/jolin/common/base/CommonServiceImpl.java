package com.jolin.common.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.CommonDTO;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.AbstractCommonServiceImpl;
import com.jolin.common.service.ICommonService;

import javax.annotation.PostConstruct;

public abstract class CommonServiceImpl<I extends ICommonDaoRepository<D>, D extends CommonDomain, DTO extends CommonDTO>
        extends AbstractCommonServiceImpl<I, D, DTO> implements ICommonService<DTO, D> {

    protected class MybatisPlusBaseServiceImpl extends ServiceImpl<I, D> {
        public void baseInit(I m){
            this.baseMapper = m;
            this.entityClass = domainClass;
            this.mapperClass = plusMapperClass;
        }
    }

    public MybatisPlusBaseServiceImpl mybatisPlusServiceImpl;

    @PostConstruct
    public void initMybatisPlusBaseServiceImpl() {
        mybatisPlusServiceImpl = new MybatisPlusBaseServiceImpl();
        mybatisPlusServiceImpl.baseInit(iBaseRepository);
    }

    /**
     * Default single table pagination for Mybatis, no encapsulation
     * Override this method for business logic that requires paging
     * @param pageDTO
     * @return
     */
    @Override
    public PageDTO<DTO> getPage(PageDTO<DTO> pageDTO) {
        return null;
    }

}

package com.jolin.common.base;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface IBaseCommonRepository<D extends BaseCommonDomain> extends ICommonDaoRepository<D> {

}

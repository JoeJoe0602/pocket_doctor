package com.jolin.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.domain.ICommonRepository;

public interface ICommonDaoRepository<D extends CommonDomain> extends ICommonRepository<D>, BaseMapper<D> {

}

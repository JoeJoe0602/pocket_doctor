package com.jolin.common.service;

import com.jolin.common.annotation.BaseJoinId;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.BaseJoinDTO;
import com.jolin.common.util.JoinIDUtil;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Association table base service
 */
public interface IBaseJoinService<DTO extends BaseJoinDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {

    default DTO beforeCreate(DTO dto) {
        IBaseCommonService.super.beforeCreate(dto);
        D domain = dtoToDomain(dto, true);
        dto.setId(JoinIDUtil.generateJoinId(domain, getDomainIdMethodGetMap()));
        return dto;
    }

    /**
     *
     *
     * In the domain, the attributes that represent ids are arranged in the order of BaseJoinId. To improve query performance, you do not need to obtain the attributes by reflection every time
     */
    List<String> getDomainIdFieldNames();

    /**
     *
     *
     * The corresponding getXXId method for the attribute representing the Id in the domain is used to improve the query performance. It does not need to be obtained by reflection every time
     */
    Map<String, Method> getDomainIdMethodGetMap();

    List<String> findFirstIdsBySecondIds(List<String> secondIds);

    List<String> findSecondIdsByFirstIds(List<String> firstIds);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findFirstIdsBySecondId(String secondId);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findSecondIdsByFirstId(String firstId);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex);
}

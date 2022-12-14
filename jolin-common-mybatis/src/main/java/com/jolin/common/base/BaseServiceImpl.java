package com.jolin.common.base;

import com.jolin.common.dto.BaseDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.IBaseService;
import com.jolin.common.util.CommonCacheUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public abstract class BaseServiceImpl<I extends IBaseMapper<D>, D extends BaseDomain, DTO extends BaseDTO>
        extends BaseCommonServiceImpl<I, D, DTO> implements IBaseService<DTO, D> {

    @Override
    public Boolean removeById(String id) throws BaseException {
        super.removeById(id);

        iBaseRepository.deleteById(id);
        afterRemove(id);
        return true;
    }

    @Override
    public Boolean removeByIds(List<String> ids) throws BaseException {
        super.removeByIds(ids);

        Set<String> cacheKeys = new HashSet<>();
        ids.forEach(id ->
                cacheKeys.add(CacheKey_dto + "::" + CommonCacheUtil.getCacheKey(getDTOClass().getSimpleName(), id))
        );

        // Delete the corresponding cache
        commonCacheUtil.mremove(cacheKeys);
        iBaseRepository.deleteBatchIds(ids);

        afterBatchRemove(ids);
        return true;
    }
}

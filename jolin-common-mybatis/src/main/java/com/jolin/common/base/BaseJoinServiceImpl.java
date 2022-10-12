package com.jolin.common.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jolin.common.annotation.BaseJoinId;
import com.jolin.common.dto.BaseJoinDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.IBaseJoinService;
import com.jolin.common.util.JoinIDUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
public abstract class BaseJoinServiceImpl<I extends IBaseJoinMapper<D>, D extends BaseJoinDomain, DTO extends BaseJoinDTO>
        extends BaseCommonServiceImpl<I, D, DTO> implements IBaseJoinService<DTO, D> {
    /**
     * In the domain, the attributes that represent ids are arranged in the order of BaseJoinId. To improve query performance, you do not need to obtain the attributes by reflection every time
     */
    List<String> domainIdFieldNames = Arrays.asList("", "", "", "", "");
    /**
     * The corresponding getXXId method for the attribute representing the Id in the domain is used to improve the query performance. It does not need to be obtained by reflection every time
     */
    Map<String, Method> domainIdMethodGetMap = new HashMap<>();

    @Override
    public List<String> getDomainIdFieldNames() {
        return domainIdFieldNames;
    }

    @Override
    public Map<String, Method> getDomainIdMethodGetMap() {
        return domainIdMethodGetMap;
    }

    @Override
    public Boolean removeById(String id) throws BaseException {
        super.removeById(id);

        deleteCache();
        iBaseRepository.deleteById(id);
        afterRemove(id);
        return true;
    }

    @Override
    public Boolean removeByIds(List<String> ids) throws BaseException {
        super.removeByIds(ids);

        deleteCache();
        iBaseRepository.deleteBatchIds(ids);
        afterBatchRemove(ids);
        return true;
    }

    @Override
    public Boolean remove(DTO dto) {
        D t = dtoToDomain(dto, true);
        dto.setId(JoinIDUtil.generateJoinId(t, getDomainIdMethodGetMap()));
        return super.remove(dto);
    }

    @Override
    public Boolean batchRemove(List<DTO> dtoList) {
        dtoList.forEach(dto -> {
            dto.setId(JoinIDUtil.generateJoinId(dtoToDomain(dto), getDomainIdMethodGetMap()));
        });
        return super.batchRemove(dtoList);
    }

    @Override
    public List<String> findFirstIdsBySecondIds(List<String> secondIds) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()), secondIds);
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstIds(List<String> firstIds) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()), firstIds);
        return ids;
    }

    @Override
    public List<String> findFirstIdsBySecondId(String secondId) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(secondId));
        return ids;
    }

    @Override
    public List<String> findSecondIdsByFirstId(String firstId) {
        List<String> ids = getResult(
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.second, getDomainIdFieldNames(), getDomainClass()),
                JoinIDUtil.getIdFieldNameByIndex(BaseJoinId.Index.first, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(firstId));
        return ids;
    }

    @Override
    public List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex) {
        return getResult("id",
                JoinIDUtil.getIdFieldNameByIndex(joinIndex, getDomainIdFieldNames(), getDomainClass()), joinIds);
    }

    @Override
    public List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex) {
        return getResult("id",
                JoinIDUtil.getIdFieldNameByIndex(joinIndex, getDomainIdFieldNames(), getDomainClass()), Arrays.asList(joinId));
    }

    private List<String> getResult(String selectIdFieldName, String whereIdFieldName, List<String> selectIdValues) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(StrUtil.toUnderlineCase(selectIdFieldName))
                .in(StrUtil.toUnderlineCase(whereIdFieldName), selectIdValues);
        List<D> domainList = iBaseRepository.selectList(queryWrapper);
        List<String> ids = new ArrayList<>();
        domainList.forEach(domain -> ids.add(JoinIDUtil.callIdFieldGetMethod(domain, getDomainIdMethodGetMap(), selectIdFieldName)));
        return ids;
    }
}

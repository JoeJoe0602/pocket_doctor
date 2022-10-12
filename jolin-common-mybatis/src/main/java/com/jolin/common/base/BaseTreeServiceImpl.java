package com.jolin.common.base;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jolin.common.dto.BaseTreeDTO;
import com.jolin.common.exception.CommonException;
import com.jolin.common.service.IBaseTreeService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseTreeServiceImpl<M extends IBaseTreeMapper<D>, D extends BaseTreeDomain, DTO extends BaseTreeDTO>
        extends BaseServiceImpl<M, D, DTO> implements IBaseTreeService<DTO, D> {

    @Override
    public List<DTO> findByParentId(String parentId) {
        QueryWrapper<D> ew = new QueryWrapper<>();
        ew.orderByAsc("order_index");
        if (StrUtil.isBlank(parentId)) {
            ew.isNull("parent_id");
        } else {
            ew.eq("parent_id", parentId);
        }
        String key = "findByParentId:" + domainClass.getName() + ":" + parentId;
        String cacheStr = commonCacheUtil.get(key);
        if (StrUtil.isNotBlank(cacheStr)) {
            //It has a cache, it returns directly
            List<DTO> dtos = JSONUtil.toList(JSONUtil.parseArray(cacheStr), dtoClass);
            return dtos;
        } else {
            List<D> parents = iBaseRepository.selectList(ew);
            List<DTO> dtos = this.domainListToDTOList(parents);
            cacheStr = JSONUtil.toJsonStr(dtos);
            //Unit s
            commonCacheUtil.set(key, cacheStr, 120L);
            return dtos;
        }
    }

    @Override
    public List<DTO> findChildrenByParentId(String parentId) {
        if (StrUtil.isBlank(parentId)) {
            throw new CommonException("parentId cannot be empty");
        }
        DTO byId = findById(parentId);
        if (byId == null) {
            throw new CommonException("Failed obtain according to parentId");
        }
        String path = byId.getPath();
        QueryWrapper<D> ew = new QueryWrapper<>();
        ew.orderByAsc("order_index");
        if (StrUtil.isBlank(path)) {
            throw new CommonException("The path attribute cannot be empty");
        } else {
            ew.likeRight("path", path + "%");
        }
        List<D> children = iBaseRepository.selectList(ew);
        List<DTO> dtos = this.domainListToDTOList(children);
        return dtos;
    }

    @Override
    public Boolean move(String currentParentId, String targetParentId, String currentId) {
        DTO currentParient = findById(currentParentId);
        if (currentParient == null) {
            throw new CommonException("The current parent node has been deleted");
        }
        DTO targetParient = findById(targetParentId);
        if (targetParient == null) {
            throw new CommonException("The target node has been deleted");
        }
        DTO currentNode = findById(currentId);
        if (currentNode == null) {
            throw new CommonException("The current node has been deleted");
        }
        if (!currentNode.getParentId().equals(currentParentId)) {
            throw new CommonException("The current node has been moved to another node");
        }
        List<DTO> childrenByPath = findChildrenByParentId(currentId);
        List<DTO> dtos = listToTree(childrenByPath, currentNode.getParentId());
        List<DTO> list = new ArrayList<>();
        int tag = -1;
        int i = 0;
        while (dtos.size() > 0) {
            DTO parient = dtos.get(i);
            System.out.println(i);
            if (tag == -1) {
                parient.setParentId(targetParient.getId());
                parient.setPath(targetParient.getPath() + "," + parient.getId());
            }
            List<DTO> childrens = parient.getChildren();
            if (!CollectionUtil.isEmpty(childrens)) {
                for (DTO child : childrens) {
                    child.setPath(parient.getPath() + "," + child.getId());
                    child.setParentId(parient.getId());
                    list.add(child);
                }
            }
            if (++i == dtos.size()) {
                tag = 0;
                dtos.clear();
                dtos.addAll(list);
                list.clear();
                i = 0;
            }
            D d = dtoToDomain(parient);
            iBaseRepository.updateById(d);
        }
        return true;
    }

    @Override
    public DTO beforeCreate(DTO dto) {
        super.beforeCreate(dto);
        String path = createPath(dto);
        dto.setPath(path);
        return dto;
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        Set<String> remIds = new HashSet<>();
        for (String id : ids) {
            List<DTO> childrenByParentId = findChildrenByParentId(id);
            List<String> collect = childrenByParentId.stream().map(DTO::getId).collect(Collectors.toList());
            remIds.addAll(collect);
        }
        List<String> list = new ArrayList<>();
        list.addAll(remIds);
        return super.beforeBatchRemove(list);
    }
}

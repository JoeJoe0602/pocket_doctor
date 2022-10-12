package com.jolin.common.service;

import cn.hutool.core.util.StrUtil;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.BaseTreeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Basic service of a tree service table
 */
public interface IBaseTreeService<DTO extends BaseTreeDTO, D extends CommonDomain> extends IBaseService<DTO, D> {

    @Transactional(rollbackFor = Exception.class)
    Boolean move(String currentParentId,String targetParentId,String currentId);

    /**
     * Find all children nodes based on the parent node id. If a null pass is passed, all levels are queried. Sons can only query one level
     * @param parentId
     * @return
     */
    List<DTO> findByParentId(String parentId);

    /**
     * Query all child nodes based on the parent node id
     * @param parentId parentId
     * @return
     */
    List<DTO> findChildrenByParentId(String parentId);

    /**
     * parentId converts the list structure to a tree structure based on the id
     * @param list List
     * @param rootNodeParentId 如果parentId为rootNodeParentId，那么把这个节点作为根节点
     * @param <T>
     * @return
     */
    default <T extends BaseTreeDTO> List<T> listToTree(Collection<T> list, String rootNodeParentId) {
        List<T> treeList = new ArrayList();
        for (T node : list) {
            // parentID can be null
            if (StrUtil.isBlank(rootNodeParentId) && StrUtil.isBlank(node.getParentId())) {
                treeList.add((findChildren(node, list)));
            } else if (node.getParentId().equals(rootNodeParentId)) {
                treeList.add((findChildren(node, list)));
            }
        }
        return treeList;
    }

    default <T extends BaseTreeDTO> T findChildren(T tree, Collection<T> list) {
        for (T node : list) {
            if (tree.getId().equals(node.getParentId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }

    default String createPath(DTO dto) {
        String parentId = dto.getParentId();
        if (StrUtil.isBlank(parentId)) {
            return dto.getId();
        } else {
            DTO parent = findById(parentId);
            String parentPath = parent.getPath();
            return parentPath + "," + dto.getId();
        }
    }
}

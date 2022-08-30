package com.jolin.service.impl;

import com.jolin.common.base.BaseJoinServiceImpl;
import com.jolin.domain.Menu;
import com.jolin.domain.RoleMenu;
import com.jolin.dto.MenuDTO;
import com.jolin.dto.RoleMenuJoinDTO;
import com.jolin.mapper.RoleMenuMapper;
import com.jolin.service.IMenuService;
import com.jolin.service.IRoleMenuService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends BaseJoinServiceImpl<RoleMenuMapper, RoleMenu, RoleMenuJoinDTO>
        implements IRoleMenuService<RoleMenu> {

    @Autowired
    private IMenuService<Menu> iMenuService;

    /**
     * 保存角色菜单的分配
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void saveRoleMenusForRest(String[] newMenuIds , List oldMenuIds, String roleId) {
        if (ArrayUtil.isEmpty(newMenuIds)) {
            newMenuIds = new String[0];
        }

        if (CollectionUtil.isEmpty(oldMenuIds)) {
            oldMenuIds = new ArrayList();
        }
        //所有被选中的ID数组转list
        List<String> newMenuIdsList = Arrays.asList(newMenuIds);

        List<String> copyNewMenuIds =  new ArrayList();
        copyNewMenuIds.addAll(newMenuIdsList);

        List<String> copyOldMeuyIds = new ArrayList<>();
        copyOldMeuyIds.addAll(oldMenuIds);




        //求oldMenuIds和newMeuyIds的差集,剩下的是需要删除的
        copyOldMeuyIds.removeAll(newMenuIdsList);
        List<RoleMenuJoinDTO> removeRoleMenuJoinDTOs = new ArrayList<>();
        for (String oldMenuId : copyOldMeuyIds) {
            RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();
            roleMenuJoinDTO.setRoleId(roleId);
            roleMenuJoinDTO.setMenuId(oldMenuId);
            removeRoleMenuJoinDTOs.add(roleMenuJoinDTO);
        }
        //如果removeRoleMenuJoinDTOs不为空批量删除
        if (CollectionUtil.isNotEmpty(removeRoleMenuJoinDTOs)) {
            batchRemove(removeRoleMenuJoinDTOs);
        }


        //求newMenuIds和oldMenuIds的差集,剩下的是需要新增的
        List<RoleMenuJoinDTO> creatRoleMenuJoinDTOs = new ArrayList<>();
        copyNewMenuIds.removeAll(oldMenuIds);
        for (String newMenuId : copyNewMenuIds) {
            RoleMenuJoinDTO roleMenuJoinDTO = new RoleMenuJoinDTO();
            roleMenuJoinDTO.setRoleId(roleId);
            roleMenuJoinDTO.setMenuId(newMenuId);
            creatRoleMenuJoinDTOs.add(roleMenuJoinDTO);
        }
        //如果creatRoleMenuJoinDTOs不为空批量新增
        if (CollectionUtil.isNotEmpty(creatRoleMenuJoinDTOs)) {
            batchCreate(creatRoleMenuJoinDTOs);
        }

    }

    @Override
    public List<String> findRoleIdsByMenuIds(List<String> menuIds) {
        return super.findFirstIdsBySecondIds(menuIds);
    }

    @Override
    public List<String> findMenuIdsByRoleIds(List<String> roleIds) {
        return super.findSecondIdsByFirstIds(roleIds);
    }

    @Override
    public void unbindRoleMenu(String menuId, String roleId) {
        List<MenuDTO> childrenByParentId = iMenuService.findChildrenByParentId(menuId);
        List<String> menuIds = childrenByParentId.stream().map(MenuDTO::getId).collect(Collectors.toList());
        iBaseRepository.deleteByMenuIdsAndRoleId(menuIds, roleId);
    }
}
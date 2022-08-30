package com.jolin.service.impl;

import com.jolin.common.base.BaseJoinServiceImpl;
import com.jolin.domain.RoleUser;
import com.jolin.dto.RoleUserJoinDTO;
import com.jolin.mapper.RoleUserMapper;
import com.jolin.service.IRoleUserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleUserServiceImpl extends BaseJoinServiceImpl<RoleUserMapper, RoleUser, RoleUserJoinDTO>
        implements IRoleUserService<RoleUser> {

    /**
     * 保存角色用户的分配,中间表的操作请使用BaseJoinController的batchSave接口.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Deprecated
    public void saveRoleUsers(String[] newUserIds, String oldUserIds, String roleId) {
        if (StrUtil.isBlank(oldUserIds)) {
            oldUserIds = "";
        }
        if (ArrayUtil.isEmpty(newUserIds)) {
            newUserIds = new String[0];
        }
        //所有被选中的ID数组转list
        List<String> newUserIdList = Arrays.asList(newUserIds);
        //旧的被选中的数组转list
        List<String> oldUserIdList = Arrays.asList(oldUserIds.split(","));



        List<String> copyNewUserIds =  new ArrayList();
        copyNewUserIds.addAll(newUserIdList);

        List<String> copyOldUserIds = new ArrayList<>();
        copyOldUserIds.addAll(oldUserIdList);

        //求oldUserIds和newUserIds的差集,剩下的是需要删除的
        copyOldUserIds.removeAll(newUserIdList);
        List<RoleUserJoinDTO> removeRoleUserJoinDTOs = new ArrayList<>();
        for (String oldUserId : copyOldUserIds) {
            RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();
            roleUserJoinDTO.setRoleId(roleId);
            roleUserJoinDTO.setUserId(oldUserId);
            removeRoleUserJoinDTOs.add(roleUserJoinDTO);
        }
        //如果removeRoleMenuJoinDTOs不为空批量删除
        if (CollectionUtil.isNotEmpty(removeRoleUserJoinDTOs)) {
            batchRemove(removeRoleUserJoinDTOs);
        }

        //求newUserIds和oldUserIds的差集,剩下的是需要新增的
        copyNewUserIds.removeAll(oldUserIdList);
        List<RoleUserJoinDTO> creatRoleUserJoinDTOs = new ArrayList<>();
        for (String newUserId : copyNewUserIds) {
            RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO();
            roleUserJoinDTO.setRoleId(roleId);
            roleUserJoinDTO.setUserId(newUserId);
            creatRoleUserJoinDTOs.add(roleUserJoinDTO);
        }
        //如果removeRoleMenuJoinDTOs不为空批量删除
        if (CollectionUtil.isNotEmpty(creatRoleUserJoinDTOs)) {
            batchCreate(creatRoleUserJoinDTOs);
        }
    }


    @Override
    public List<String> findUserIdsByRoleIds(List<String> roleIds) {
        return super.findSecondIdsByFirstIds(roleIds);
    }

    @Override
    public List<String> findRoleIdsByUserIds(List<String> userIds) {
        return super.findFirstIdsBySecondIds(userIds);
    }
}
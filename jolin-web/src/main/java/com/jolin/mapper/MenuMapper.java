package com.jolin.mapper;

import com.jolin.common.base.IBaseTreeMapper;
import com.jolin.domain.Menu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper extends IBaseTreeMapper<Menu> {

    /**
     * 分页
     */
    IPage<Menu> selectPage(@Param("page") Page page, @Param("menu") Menu menu);

    List<Menu> findByRoleId(String roleId);

    List<String> findIdsByMenuUrl(String menuUrl);

    List<Menu> findMenusByRoleIds(@Param("roleIds") List<String> roleIds);

    List<Menu> findChildrenByParentId(String parentId);

    Set<String> findButtonByRoleIds(List roleIds);
}

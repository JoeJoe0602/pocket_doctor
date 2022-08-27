package com.jolin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.CommonSecurityService;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.common.util.CommonStatic;
import com.jolin.domain.UserInfo;
import com.jolin.dto.UserInfoDTO;
import com.jolin.exception.SysException;
import com.jolin.mapper.UserInfoMapper;
import com.jolin.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {


    @Autowired
    private CommonCacheUtil commonCacheUtil;


    @Override
    public UserInfoDTO findById(String id) throws BaseException {
        UserInfoDTO userInfoDTO = super.findById(id);
        if (userInfoDTO != null) {
            userInfoDTO.setPassword(null);
        }
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO findWithPasswordById(String id) throws BaseException {
        return super.findById(id);
    }


    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        UserInfo userInfo = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserInfo> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userInfo);
        pageDTO = CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, UserInfoDTO.class, pageDTO);
        return pageDTO;
    }


    @Override
    public String findIdByLoginName(String loginName) throws BaseException {
        return iBaseRepository.findIdByLoginName(loginName);
    }

    @Override
    public UserInfo findByLoginName(String loginName) throws BaseException {
        return iBaseRepository.findByLoginName(loginName);
    }

    @Override
    public UserInfoDTO beforeCreate(UserInfoDTO dto) throws BaseException {
        dto = super.beforeCreate(dto);
        if (StrUtil.isNotBlank(dto.getUsername())) {
            String userId = findIdByLoginName(dto.getUsername());
            if (StrUtil.isNotBlank(userId)) {
                throw new SysException("用户名已经存在");
            }
        }
        // 设置默认密码
        String password = StrUtil.isBlank(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(CommonSecurityService.instance.encodePassword(password));
        dto.setState("1");
        return dto;
    }


    @Override
    //    @PreAuthorize("hasAuthority('" + CommonStatic.ROLE_ID_KEY + "')")
    //TODO 注释掉 PreAuthorize 待完善鉴权系统来替代此方法
    public Boolean afterRemove(Integer id) {
        deleteCacheOfFindIdByLoginName();
        return super.afterRemove(id);
    }

    @Override
    public UserInfoDTO beforeUpdate(UserInfoDTO dto) throws BaseException {
        dto = super.beforeUpdate(dto);
        deleteCacheOfFindIdByLoginName();
        if (StrUtil.isNotBlank(dto.getPassword())) {
            dto.setPassword(CommonSecurityService.instance.encodePassword(dto.getPassword()));
        }
        return dto;
    }

    private void deleteCacheOfFindIdByLoginName() {
        //有删除操作，则直接删除所有findIdByLoginName缓存
        commonCacheUtil.removeByPattern(CacheKey_findIdByLoginName + "*");
    }
}

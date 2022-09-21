package com.jolin.service.impl;

import com.jolin.common.base.BaseServiceImpl;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.CommonSecurityService;
import com.jolin.common.sms.SmsThirdSendService;
import com.jolin.common.util.CommonCacheUtil;
import com.jolin.common.util.CommonMybatisPageUtil;
import com.jolin.common.util.CommonStatic;
import com.jolin.domain.UserInfo;
import com.jolin.dto.*;
import com.jolin.exception.SysException;
import com.jolin.mapper.UserInfoMapper;
import com.jolin.service.*;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {



    @Autowired
    private CommonCacheUtil commonCacheUtil;



    @Override
    public PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        UserInfoDTO userInfoDTO = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.getUserOnlyByRoleIdOrDeptIdPage(page, userInfoDTO);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(pageList, UserInfoDTO.class, pageDTO);
        return resultPage;
    }

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
    public Boolean checkUserExist(String loginName) {
        String s = iBaseRepository.checkUserExist(loginName);
        if (StrUtil.isNotBlank(s)) {
            return true;
        }
        return false;
    }





    @Override
    public Boolean retrievePassword(RetrievePasswordDTO retrievePassword) {

            String password = retrievePassword.getPassword();
            String confirm = retrievePassword.getConfirm();
            if (StrUtil.isBlank(password) || !password.equals(confirm)) {
                throw new SysException("新密码和确认密码不相等");
            }
            UserInfo userInfo = iBaseRepository.findByLoginName(retrievePassword.getLoginName());
            if(!CommonSecurityService.instance.match(retrievePassword.getOldPassword(), userInfo.getPassword())){
                throw new SysException("密码不正确");
            }
            userInfo.setPassword(CommonSecurityService.instance.encodePassword(password));
            iBaseRepository.updateById(userInfo);
            commonCacheUtil.remove(retrievePassword.getLoginName() + ":emailcode");

            return true;

    }

    @Override
    public Boolean confirmCode(ConfirmCodeDTO confirmCodeDTO) {
        String code = "";
        String email = confirmCodeDTO.getEmail();
        String phoneNum = confirmCodeDTO.getPhoneNum();
        if (confirmCodeDTO.getType() == 1) {
            if (StrUtil.isBlank(email)) {
                throw new SysException("邮箱找回密码,email不能为空");
            }
            code = commonCacheUtil.get(email + ":emailcode");
            if (StrUtil.isBlank(code) || !code.equals(confirmCodeDTO.getCode())) {
                throw new SysException("验证码不正确");
            }
            //验证成功后,为防止在修改密码期间过期续约
            commonCacheUtil.set(email + ":emailcode", code, 300000);
            return true;
        }

        if (confirmCodeDTO.getType() == 0) {
            if (StrUtil.isBlank(phoneNum)) {
                throw new SysException("手机号不能为空");
            }
            code = commonCacheUtil.get(phoneNum + ":phonecode");
            if (StrUtil.isBlank(code) || !code.equals(confirmCodeDTO.getCode())) {
                throw new SysException("验证码不正确");
            }
            //验证成功后,为防止在修改密码期间过期续约
            commonCacheUtil.set(phoneNum + ":phonecode", code, 300000);
            return true;
        }

        if (confirmCodeDTO.getType() == 2) {
            if (StrUtil.isBlank(email)) {
                throw new SysException("email不能为空");
            }
            code = commonCacheUtil.get(email + ":emailregister");
            if (StrUtil.isBlank(code) || !code.equals(confirmCodeDTO.getCode())) {
                throw new SysException("验证码不正确");
            }
            //验证成功后,为防止在修改密码期间过期续约
            commonCacheUtil.set(email + ":emailregister", code, 300000);
            return true;
        }

        return false;

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
    public UserInfoDTO beforeCreate(UserInfoDTO dto) throws BaseException {
        dto = super.beforeCreate(dto);
        if (StrUtil.isNotBlank(dto.getLoginName())) {
            String userId = findIdByLoginName(dto.getLoginName());
            if (StrUtil.isNotBlank(userId)) {
                throw new SysException("用户名已经存在");
            }
        }

        // 设置默认密码
        String password = StrUtil.isBlank(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(CommonSecurityService.instance.encodePassword(password));
        dto.setState("1");
        dto.setIsAdmin(0);
        return dto;
    }


    @Override
    //    @PreAuthorize("hasAuthority('" + CommonStatic.ROLE_ID_KEY + "')")
    //TODO 注释掉 PreAuthorize 待完善鉴权系统来替代此方法
    public Boolean afterRemove(String id) {
        deleteCacheOfFindIdByLoginName();
        return super.afterRemove(id);
    }

    @Override
    public Boolean afterBatchRemove(List<String> ids) {
        deleteCacheOfFindIdByLoginName();
        return super.afterBatchRemove(ids);
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

    @Override
    public UserInfo findByLoginName(String loginName) {
        return iBaseRepository.findByLoginName(loginName);
    }
}

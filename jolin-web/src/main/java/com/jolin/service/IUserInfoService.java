package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.IBaseService;
import com.jolin.dto.*;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 用户Service
 */
public interface IUserInfoService<D extends CommonDomain> extends IBaseService<UserInfoDTO, D> {
    String CacheKey_findIdByLoginName = "findIdByLoginName";
    String CacheKey_findIdByPhoneNum = "findIdByPhoneNum";
    String CacheKey_findIdByEmail = "findIdByEmail";

    /**
     * 带有角色的用户保存(为community单独的方法)
     */
    String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds);

    /**
     * 根据用户登录名查询用户
     */
    @Cacheable(value = CacheKey_findIdByLoginName, key = "#loginName", unless = "#result==null")
    String findIdByLoginName(String loginName);

    /**
     * 根据手机号登陆名查询用户
     */
    @Cacheable(value = CacheKey_findIdByPhoneNum, key = "#phoneNum", unless = "#result==null")
    String findIdByPhoneNum(String phoneNum);

    /**
     * 根据手机号登陆名查询用户
     */
    @Cacheable(value = CacheKey_findIdByEmail, key = "#email", unless = "#result==null")
    String findIdByEmail(String email);

    PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO);

    UserInfoDTO findWithPasswordById(String id);

    UserInfoDTO findByPhoneNum(String phoneNum);

    Boolean checkUserExist(String loginName);

    Boolean sendEmailCode(EmailDTO email);

    Boolean retrievePassword(RetrievePasswordDTO retrievePassword);

    Boolean confirmCode(ConfirmCodeDTO confirmCodeDTO);

    Boolean sendPhoneCode(String phoneNum);

    Boolean phoneRegisterCode(String phoneNum);

    Boolean emailRegisterCode(EmailDTO email);

    Boolean emailRegister(EmailRegisterDTO registerDTO);

    Boolean phoneRegister(@Valid PhoneRegisterDTO phoneRegisterDTO);

    Set<String> findUserButton();

    Boolean addUser(UserInfoDTO userInfoDTO);
}
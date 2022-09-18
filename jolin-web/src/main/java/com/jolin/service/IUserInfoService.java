package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.service.IBaseService;
import com.jolin.domain.UserInfo;
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
     * 根据用户登录名查询用户
     */
    @Cacheable(value = CacheKey_findIdByLoginName, key = "#loginName", unless = "#result==null")
    String findIdByLoginName(String loginName);



    PageDTO<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(PageDTO<UserInfoDTO> pageDTO);

    UserInfoDTO findWithPasswordById(String id);


    Boolean checkUserExist(String loginName);


    Boolean retrievePassword(RetrievePasswordDTO retrievePassword);

    Boolean confirmCode(ConfirmCodeDTO confirmCodeDTO);

    UserInfo findByLoginName(String loginName);







}
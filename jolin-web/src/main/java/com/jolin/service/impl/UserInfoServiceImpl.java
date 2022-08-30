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
import com.jolin.mapper.MenuMapper;
import com.jolin.mapper.UserInfoMapper;
import com.jolin.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo, UserInfoDTO> implements IUserInfoService<UserInfo> {

    @Autowired
    private IDeptUserService iDeptUserService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;


    @Autowired
    private SmsThirdSendService thirdSendService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private MenuMapper menuMapper;


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
    public UserInfoDTO findByPhoneNum(String phoneNum) {
        UserInfo u = iBaseRepository.findByPhoneNum(phoneNum);
        return domainToDTO(u);
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
    public Boolean sendEmailCode(EmailDTO email) throws BaseException {
        String code = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        UserInfo userInfo = iBaseRepository.findByEmail(email.getTo());
        if (userInfo == null || StrUtil.isBlank(userInfo.getEmail())) {
            throw new SysException("当前邮箱没有绑定账号");
        }
        String text = email.getAlias() + ": 尊敬的用户,您当前修改密码的验证码为【?】五分钟之内有效.";
        email.setText(text);
        email.setTo(userInfo.getEmail());
        try {
            sendEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("邮件发送失败,请检查邮件配置是否正确");
        }
        commonCacheUtil.set(email.getTo() + ":emailcode", code, 300000);
        return true;
    }

    @Override
    public Boolean sendPhoneCode(String phoneNum) throws BaseException {
        String code = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        UserInfo userInfo = iBaseRepository.findByPhoneNum(phoneNum);
        if (userInfo == null || StrUtil.isBlank(userInfo.getPhoneNum())) {
            throw new SysException("当前手机号没有绑定账号");
        }
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("phoneNum", phoneNum);
        sendData.put("code", code);
        sendData.put("type", 1);
        Boolean send = false;
        try {
            send = thirdSendService.send(sendData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("调用第三方发送修改密码验证码异常");
        }

        if (send) {
            commonCacheUtil.set(phoneNum + ":phonecode", code, 300000);
            return true;
        }

        return send;

    }

    @Override
    public Boolean phoneRegisterCode(String phoneNum) {
        String code = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        if (StrUtil.isBlank(phoneNum)) {
            throw new SysException("手机号不能为空");
        }
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("phoneNum", phoneNum);
        sendData.put("code", code);
        sendData.put("type", 2);
        Boolean send = false;
        try {
            send = thirdSendService.send(sendData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("调用第三方发送修改密码验证码异常");
        }

        if (send) {
            commonCacheUtil.set(phoneNum + ":phoneregister", code, 300000);
            return true;
        }

        return send;
    }

    @Override
    public Boolean emailRegisterCode(EmailDTO email) {
        String code = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        String text = email.getAlias() + ": 尊敬的用户,您当前注册的验证码为【?】,五分钟之内有效.";
        email.setText(text);
        email.setTo(email.getTo());
        try {
            sendEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("邮件发送失败,请检查邮件配置是否正确");
        }
        commonCacheUtil.set(email.getTo() + ":emailregister", code, 300000);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean emailRegister(EmailRegisterDTO registerDTO) {
        String code = commonCacheUtil.get(registerDTO.getEmail()+":emailregister");
        if (!StrUtil.equals(code, registerDTO.getCode())) {
            throw new SysException("验证码不正确");
        }
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setEmail(registerDTO.getEmail());
        userInfo.setLoginName(registerDTO.getUsername());
        userInfo.setFlag(1);
        userInfo.setState("1");
        //用户所属角色填充
        List<String> roleIds = new ArrayList();
        //设置默认角色
        RoleDTO role = roleService.findByRoleName("ROLE_USER");
        roleIds.add(role.getId());
        //入库
        String id = saveUserWithRoles(userInfo, roleIds);
        commonCacheUtil.remove(registerDTO.getEmail()+":emailregister");
        return true;
    }

    @Override
    public Boolean phoneRegister(@Valid PhoneRegisterDTO phoneRegisterDTO) {
        String code = commonCacheUtil.get(phoneRegisterDTO.getPhoneNum()+":phoneregister");
        if (!StrUtil.equals(phoneRegisterDTO.getCode(), code)) {
            throw new SysException("验证码不正确");
        }
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setPhoneNum(phoneRegisterDTO.getPhoneNum());
        userInfo.setLoginName(phoneRegisterDTO.getUsername());
        userInfo.setFlag(1);
        userInfo.setState("1");
        //用户所属角色填充
        List<String> roleIds = new ArrayList();
        //设置默认角色
        RoleDTO role = roleService.findByRoleName("ROLE_USER");
        roleIds.add(role.getId());
        //入库
        String id = saveUserWithRoles(userInfo, roleIds);
        commonCacheUtil.remove(phoneRegisterDTO.getPhoneNum()+":phoneregister");
        return true;
    }

    @Override
    public Set<String> findUserButton() {
        String currentLoginName = CommonSecurityService.instance.getCurrentLoginName();
        String userId = iBaseRepository.findIdByLoginName(currentLoginName);
        List roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
        Set<String> menus = menuMapper.findButtonByRoleIds(roleIds);
        return menus;
    }

    @Override
    public Boolean addUser(UserInfoDTO userInfoDTO) {
        //用户所属角色填充
        List<String> roleIds = new ArrayList();
        //设置默认角色
        RoleDTO role = roleService.findByRoleName("ROLE_USER");
        roleIds.add(role.getId());
        //入库
        String id = saveUserWithRoles(userInfoDTO, roleIds);
        return true;
    }

    @Override
    public Boolean retrievePassword(RetrievePasswordDTO retrievePassword) {
        Integer type = retrievePassword.getType();
        if (type == 1) {
            String email = retrievePassword.getEmail();
            String code = commonCacheUtil.get(email + ":emailcode");
            if (StrUtil.isBlank(retrievePassword.getCode()) || !retrievePassword.getCode().equals(code)) {
                throw new SysException("非法修改");
            }
            String password = retrievePassword.getPassword();
            String confirm = retrievePassword.getConfirm();
            if (StrUtil.isBlank(password) || !password.equals(confirm)) {
                throw new SysException("新密码和确认密码不相等");
            }
            UserInfo userInfo = iBaseRepository.findByEmail(email);
            iBaseRepository.updateById(userInfo);
            commonCacheUtil.remove(email + ":emailcode");
            return true;
        }

        if (type == 0) {
            String phoneNum = retrievePassword.getPhoneNum();
            String code = commonCacheUtil.get(phoneNum + ":phonecode");
            if (!retrievePassword.getCode().equals(code)) {
                throw new SysException("非法修改");
            }
            String password = retrievePassword.getPassword();
            String confirm = retrievePassword.getConfirm();
            if (StrUtil.isBlank(password) || !password.equals(confirm)) {
                throw new SysException("新密码和确认密码不相等");
            }
            UserInfo userInfo = iBaseRepository.findByPhoneNum(phoneNum);
            userInfo.setPassword(CommonSecurityService.instance.encodePassword(password));
            iBaseRepository.updateById(userInfo);
            commonCacheUtil.remove(phoneNum + ":phonecode");
            return true;
        }


        return false;
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

    private Boolean sendEmail(EmailDTO email, String code) throws UnsupportedEncodingException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String fromByte = new String((email.getAlias() + "<" + email.getFrom() + ">").getBytes("UTF-8"));
        simpleMailMessage.setFrom(fromByte);
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getSubject());
        String text = email.getText().replace("?", code);
        simpleMailMessage.setText(text);
        return true;
    }

    @Override
    public PageDTO<UserInfoDTO> getPage(PageDTO<UserInfoDTO> pageDTO) {
        UserInfo userInfo = getDomainFilterFromPageDTO(pageDTO);
        IPage<UserInfo> data = iBaseRepository.selectPage(CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO), userInfo);
        pageDTO = CommonMybatisPageUtil.getInstance().iPageToPageDTO(data, UserInfoDTO.class, pageDTO);
        return pageDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveUserWithRoles(UserInfoDTO dto, List<String> roleIds) {
        if (dto == null) {
            return null;
        }
        String userId = null;
        if (dto.getId() == null) {
            userId = create(dto).getId();
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<RoleUserJoinDTO> roleUserJoinDTOS = new ArrayList<>();
                for (String roleId : roleIds) {
                    RoleUserJoinDTO roleUserJoinDTO = new RoleUserJoinDTO(roleId, userId);
                    roleUserJoinDTOS.add(roleUserJoinDTO);
                }

                iRoleUserService.batchCreate(roleUserJoinDTOS);
            }
        } else {
            userId = update(dto).getId();
        }

        return userId;
    }

    @Override
    public String findIdByLoginName(String loginName) throws BaseException {
        return iBaseRepository.findIdByLoginName(loginName);
    }

    @Override
    public String findIdByPhoneNum(String phoneNum) {
        return iBaseRepository.findIdByPhoneNum(phoneNum);
    }

    @Override
    public String findIdByEmail(String email) {
        return iBaseRepository.findIdByEmail(email);
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
        if (StrUtil.isNotBlank(dto.getPhoneNum())) {
            String phoneNum = findIdByPhoneNum(dto.getPhoneNum());
            if (StrUtil.isNotBlank(phoneNum)) {
                throw new SysException("手机号已经被绑定");
            }
        }
        if (StrUtil.isNotBlank(dto.getEmail())) {
            String email = findIdByEmail(dto.getEmail());
            if (StrUtil.isNotBlank(email)) {
                throw new SysException("邮箱已经被绑定");
            }
        }
        // 设置默认密码
        String password = StrUtil.isBlank(dto.getPassword()) ? CommonStatic.DEFAULT_PASSWORD : dto.getPassword();
        dto.setPassword(CommonSecurityService.instance.encodePassword(password));
        dto.setState("1");
        return dto;
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(ids);
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(ids);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            throw new SysException(messageSource.getMessage("exception.containRole", null, LocaleContextHolder.getLocale()));
        }
        if (CollectionUtil.isNotEmpty(deptIds)) {
            throw new SysException(messageSource.getMessage("exception.containDept", null, LocaleContextHolder.getLocale()));
        }
        return true;
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
}

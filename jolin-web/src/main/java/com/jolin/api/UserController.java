package com.jolin.api;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jolin.common.api.BaseController;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.dto.*;
import com.jolin.exception.SysException;
import com.jolin.service.IFileStorageService;
import com.jolin.service.IUserInfoService;
import com.jolin.web.BaseWebMvcConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@RestController
@Api(tags = {"02.用户操作接口"})
@ApiSupport(order = 2)
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserInfoService, UserInfoDTO> {

    @Autowired
    private IFileStorageService storageService;

    @ApiOperation(value = "8.用户、角色、部门多表分页查询。")
    @ApiOperationSupport(order = 8)
    @PostMapping(value = "/composite-search")
    public ResultDTO<PageDTO<UserInfoDTO>> compositeSelectByPage(@Valid @RequestBody PageDTO<UserInfoDTO> pageDTO) {
        pageDTO = iBaseService.getUserOnlyByRoleIdOrDeptIdPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    @ApiOperation(value = "9.根据loginName查询用户Id")
    @ApiOperationSupport(order = 9)
    @GetMapping(value = "/findIdByLoginName")
    public ResultDTO<String> findIdByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        return new ResultDTO(userId);
    }

    @ApiOperation(value = "10.根据loginName查询用户信息")
    @ApiOperationSupport(order = 10)
    @GetMapping(value = "/findByLoginName")
    public ResultDTO<UserInfoDTO> findByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        UserInfoDTO dto = iBaseService.findWithPasswordById(userId);
        return new ResultDTO(dto);
    }

    @ApiOperation(value = "11.执行用户头像上传")
    @ApiOperationSupport(order = 11)
    @PostMapping(value = "/user-upload-avatar-rest")
    public ResultDTO avatarUploadRest(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws IOException {
        if (StrUtil.isBlank(id)) {
            throw new SysException("用户ID不能为空!");
        }
        if (file.isEmpty()) {
            throw new SysException("上传文件不能为空");
        }

        log.info("avatarUploadRest_fileName{}", file.getBytes().length);
        if (file.getBytes().length > 1024 * 1024 * 2) {
            throw new SysException("图片大小不可超过2M!");
        }
        FileStorageDTO fileStorageDTO = storageService.saveFile(file, BaseWebMvcConfigurer.secondLevelImgDirectory + File.separator,
                file.getOriginalFilename());

        UserInfoDTO dto = new UserInfoDTO();
        dto.setAvatar(BaseWebMvcConfigurer.secondLevelImgUrl + "/" + fileStorageDTO.getFileName());
        dto.setId(id);
        iBaseService.update(dto);
        return new ResultDTO(dto.getAvatar());
    }

    @ApiOperation(value = "12.查询用户是否存在")
    @ApiOperationSupport(order = 12)
    @GetMapping(value = "/checkUserExist")
    public ResultDTO<Boolean> checkUserExist(String loginName) {
        Boolean exist = iBaseService.checkUserExist(loginName);
        return new ResultDTO(exist);
    }

    @ApiOperation(value = "13.发送邮箱验证码用于密码找回")
    @ApiOperationSupport(order = 13)
    @PostMapping(value = "/send-email-code")
    public ResultDTO<Boolean> sendEmailCode(@Valid @RequestBody EmailDTO email) {
        Boolean reuslt = iBaseService.sendEmailCode(email);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "14.发送手机验证码用于密码找回")
    @ApiOperationSupport(order = 14)
    @GetMapping(value = "/send-phone-code")
    public ResultDTO<Boolean> sendPhoneCode(String phoneNum) {
        Boolean reuslt = iBaseService.sendPhoneCode(phoneNum);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "15.修改密码")
    @ApiOperationSupport(order = 15)
    @PostMapping(value = "/retrieve-password")
    public ResultDTO<Boolean> retrievePassword(@Valid @RequestBody RetrievePasswordDTO retrievePassword) {
        Boolean reuslt = iBaseService.retrievePassword(retrievePassword);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "16.确认验证码是否正确")
    @ApiOperationSupport(order = 16)
    @PostMapping(value = "/confirm-code")
    public ResultDTO<Boolean> confirmCode(@Valid @RequestBody ConfirmCodeDTO confirmCodeDTO) {
        Boolean reuslt = iBaseService.confirmCode(confirmCodeDTO);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "17.邮箱注册")
    @ApiOperationSupport(order = 17)
    @PostMapping(value = "/email-register")
    public ResultDTO<Boolean> emailRegister(@Valid @RequestBody EmailRegisterDTO emailRegisterDTO) {
        Boolean reuslt = iBaseService.emailRegister(emailRegisterDTO);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "18.发送邮箱注册验证码")
    @ApiOperationSupport(order = 18)
    @PostMapping(value = "/email-register-code")
    public ResultDTO<Boolean> emailRegisterCode(@Valid @RequestBody EmailDTO email) {
        Boolean reuslt = iBaseService.emailRegisterCode(email);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "19.发送手机注册验证码")
    @ApiOperationSupport(order = 19)
    @PostMapping(value = "/phone-register-code")
    public ResultDTO<Boolean> phoneRegisterCode(String phoneNum) {
        Boolean reuslt = iBaseService.phoneRegisterCode(phoneNum);
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "20.手机号注册")
    @ApiOperationSupport(order = 20)
    @PostMapping(value = "/phone-register")
    public ResultDTO<Boolean> emailRegister(@Valid @RequestBody PhoneRegisterDTO phoneRegisterDTO) {
        Boolean reuslt = iBaseService.phoneRegister(phoneRegisterDTO);
        return new ResultDTO(reuslt);
    }


    @ApiOperation(value = "21.查询用户按钮权限")
    @ApiOperationSupport(order = 21)
    @PostMapping(value = "/find-user-button")
    public ResultDTO<Set> findUserButton() {
        Set<String> reuslt = iBaseService.findUserButton();
        return new ResultDTO(reuslt);
    }

    @ApiOperation(value = "22.新增用户")
    @ApiOperationSupport(order = 21)
    @PostMapping(value = "/add-user")
    public ResultDTO<Set> addUser(@RequestBody UserInfoDTO userInfoDTO) {
        Boolean reuslt = iBaseService.addUser(userInfoDTO);
        return new ResultDTO(reuslt);
    }

}
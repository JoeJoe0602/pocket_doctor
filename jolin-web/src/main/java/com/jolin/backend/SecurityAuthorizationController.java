package com.jolin.backend;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jolin.common.dto.ResultDTO;
import com.jolin.common.service.CommonSecurityService;
import com.jolin.dto.HealthRecordDTO;
import com.jolin.dto.RetrievePasswordDTO;
import com.jolin.dto.UserInfoDTO;
import com.jolin.exception.SysException;
import com.jolin.service.IHealthRecordService;
import com.jolin.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jolin
 * @version 1.0
 * @date 2021/4/15
 * @describe
 * This class only shows login-related documents in swagger. In fact, when you make a request in swagger, you use security's custom filter
 */
@RestController
@Api(tags = {"01.auth"})
@ApiSupport(order = -1)
public class SecurityAuthorizationController {

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IHealthRecordService iHealthRecordService;

    @ApiOperation(value = "1.login")
    @ApiOperationSupport(order = 1)
    @PostMapping(value = "/login")
    public void userNamePassWord(String username,String password) {

    }


    @ApiOperation(value = "2.register")
    @ApiOperationSupport(order = 2)
    @PostMapping(value = "/register")
    public ResultDTO register(@RequestBody @Valid UserInfoDTO userInfoDTO){

        if(!userInfoDTO.getPassword().equals(userInfoDTO.getConfirmPassword())){
            throw new SysException("password must be same!");
        }
       UserInfoDTO userInfoDTO1= (UserInfoDTO) iUserInfoService.create(userInfoDTO);

        HealthRecordDTO healthRecordDTO=new HealthRecordDTO();
        healthRecordDTO.setUserId(userInfoDTO1.getId());
        iHealthRecordService.create(healthRecordDTO);
        return new ResultDTO(userInfoDTO1);
    }

    @ApiOperation(value = "3.retrievePassword")
    @ApiOperationSupport(order = 3)
    @PostMapping(value = "/retrievePassword")
    public ResultDTO retrievePassword(@RequestBody @Valid RetrievePasswordDTO retrievePasswordDTO){

        return new ResultDTO(iUserInfoService.retrievePassword(retrievePasswordDTO));
    }

    @ApiOperation(value = "4.getUserInfo")
    @ApiOperationSupport(order = 4)
    @PostMapping(value = "/getUserInfo")
    public ResultDTO getUserInfo(){

        return new ResultDTO(iUserInfoService.findByLoginName(CommonSecurityService.instance.getCurrentLoginName()));
    }


}

package com.jolin.api;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jolin
 * @version 1.0
 * @date 2021/4/15
 * @describe
 * 该类只为在swagger中展示登陆相关文档,实际上在swagger中发起请求时走的是security的自定义filter
 */
@RestController
@Api(tags = {"01.登录"})
@ApiSupport(order = -1)
public class SecurityAuthorizationController {

    @ApiOperation(value = "1.用户名密码登录")
    @ApiOperationSupport(order = 1)
    @PostMapping(value = "/login")
    public void userNamePassWord(String username,String password) {

    }
}

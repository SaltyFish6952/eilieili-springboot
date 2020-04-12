package com.springboot.eilieili.demo.controller;


import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.Audience;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.mapper.UserMapper;
import com.springboot.eilieili.demo.util.JwtTokenUtil;
import com.springboot.eilieili.demo.util.PasswordUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/12
 */
@Slf4j
@RestController

public class LoginController {

    @Autowired
    private Audience audience;

    @Autowired(required = false)
    private UserMapper userMapper;


    @PostMapping("/api/login")
    @JwtIgnore
    public Result adminLogin(HttpServletResponse response, @RequestBody JSONObject userData) {

        //解密，验证密码
        try {

            String postPassword = PasswordUtil.Decrypt(userData.getString("password"));
            String databasePassword = PasswordUtil.Decrypt(
                    userMapper.getUserPasswordByAccount(userData.getString("userAccount")));

            if (!postPassword.equals(databasePassword)) {
                return new Result(ResultCode.USER_LOGIN_ERROR);
            }

        } catch (Exception e) {
            log.error("解密错误：{}", e.getMessage());
            return new Result(ResultCode.USER_LOGIN_ERROR);
        }


        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
//        String role = "admin";
        log.info(audience.getName());
        // 创建token
        String token = JwtTokenUtil.createJWT(userId, userData.getString("userAccount"), audience);
//        String token = JwtTokenUtil.createJWT(userId, userName, role, audience);
        log.info("### 登录成功, token={} ###", token);

        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);

        //获取用户数据
        User user = userMapper.getUserByAccount(userData.getString("userAccount"));
        result.put("user", user);
        return Result.SUCCESS(result);
    }

//    @GetMapping("/api/users")
//    public Result userList() {
//        log.info("### 查询所有用户列表 ###");
//        return Result.SUCCESS();
//    }


}

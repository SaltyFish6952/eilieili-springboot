package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/12
 */
@Slf4j
@RestController
public class UserController {

    @Autowired(required = false)
    UserMapper userMapper;


    @GetMapping("/api/user/")
    public Result getUser(String userAccount) {

        User user = userMapper.getUserByAccount(userAccount);

        if (user == null){
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("user", user);

        return Result.SUCCESS(result);

    }


}

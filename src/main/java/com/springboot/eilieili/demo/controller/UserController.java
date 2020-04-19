package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @JwtIgnore
    @GetMapping("/api/user")
    public Result getUser(String userId) {

        if (userId == null) {
            return new Result(ResultCode.PARAM_IS_BLANK);
        }

        User user = userMapper.getUserById(userId);

        if (user == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("user", user);

        return Result.SUCCESS(result);

    }


    @GetMapping("/api/user/check/name")
    public Result checkUserNameValid(String checkName, String userId) {

        if (checkName == null) {
            return new Result(ResultCode.PARAM_IS_BLANK);
        }

        Integer count = userMapper.checkUserName(checkName, userId);
        JSONObject result = new JSONObject();

        result.put("isValid", count == 0);

        return Result.SUCCESS(result);
    }

    @GetMapping("/api/user/check/password")
    public Result checkUserPasswordValid(String checkPassword, String userId) {

        if (checkPassword == null) {
            return new Result(ResultCode.PARAM_IS_BLANK);
        }

        Integer count = userMapper.checkUserPassword(userId, checkPassword);
        JSONObject result = new JSONObject();

        result.put("isValid", count == 1);

        return Result.SUCCESS(result);
    }


    @PostMapping("/api/user/update/name")
    public Result updateUserName(@RequestBody JSONObject data) {

        String newName = data.getString("newName");
        String userId = data.getString("userId");

        if (newName == null || userId == null) {
            return new Result(ResultCode.PARAM_NOT_COMPLETE);
        }

        try {
            userMapper.updateUserName(userId, newName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL();
        }

        return Result.SUCCESS();


    }



    @PostMapping("/api/user/update/password")
    public Result updateUserPassword(@RequestBody JSONObject data) {

        String newPassword = data.getString("newPassword");
        String userId = data.getString("userId");

        if (newPassword == null || userId == null) {
            return new Result(ResultCode.PARAM_NOT_COMPLETE);
        }

        try {
            userMapper.updateUserPassword(userId, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL();
        }

        return Result.SUCCESS();

    }


}

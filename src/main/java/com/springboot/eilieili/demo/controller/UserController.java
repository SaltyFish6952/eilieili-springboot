package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.bean.UserExtend;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.UserMapper;
import com.springboot.eilieili.demo.util.PasswordUtil;
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

    @JwtIgnore
    @GetMapping("/api/user/check/name")
    public Result checkUserNameValid(String checkName, String userId) {

        if (checkName == null) {
            return new Result(ResultCode.PARAM_IS_BLANK);
        }

        Integer count;
        JSONObject result = new JSONObject();


        if (userId == null) {

            count = userMapper.checkNewUserName(checkName);

        } else {
            count = userMapper.checkUserName(checkName, userId);
        }

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


    @JwtIgnore
    @GetMapping("/api/user/check/account")
    public Result checkUserAccount(String userAccount) {

        if (userAccount == null) {
            return new Result(ResultCode.PARAM_IS_BLANK);
        }

        Integer count = userMapper.checkNewUserAccount(userAccount);
        JSONObject res = new JSONObject();

        res.put("isValid", count == 0);
        return Result.SUCCESS(res);

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

    @JwtIgnore
    @PostMapping("/api/user/register")
    public Result registerUser(@RequestBody JSONObject user) {

        String userName = user.getString("userName");
        String userAccount = user.getString("userAccount");
        String userPassword = user.getString("userPassword");

        if (userAccount == null || userName == null || userPassword == null)
            return new Result(ResultCode.PARAM_NOT_COMPLETE);

        try {

            userMapper.insertUser(userName, userAccount, userPassword);

            return Result.SUCCESS();

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return Result.FAIL(e.getMessage());
        }

    }

    @JwtIgnore
    @GetMapping("/api/user/all")
    public Result getAllUsers() {

        try {

            UserExtend[] users = userMapper.getAllUsers();
            JSONObject res = new JSONObject();
            res.put("users", users);

            return Result.SUCCESS(res);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return Result.FAIL();
        }

    }

    @PutMapping("/api/user")
    public Result updateUser(@RequestBody JSONObject data) {
        try {

            String userId = data.getString("userId");
            String userName = data.getString("userName");
            String userAccount = data.getString("userAccount");
            String userPassword = data.getString("userPassword");

            userMapper.updateUser(userId, userName, userAccount, userPassword);

            return Result.SUCCESS();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

    @DeleteMapping("/api/user")
    public Result deleteUser(@RequestBody JSONObject data) {
        try {

            String userId = data.getString("userId");

            userMapper.deleteUser(userId);
            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }


}

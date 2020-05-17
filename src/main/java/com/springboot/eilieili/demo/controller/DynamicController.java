package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Dynamic;
import com.springboot.eilieili.demo.bean.DynamicReply;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.DynamicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class DynamicController {

    @Autowired(required = false)
    DynamicMapper dynamicMapper;

    @JwtIgnore
    @GetMapping("api/dynamic")
    public Result getDynamic(String userId, Integer page) {

        try {


            JSONObject res = new JSONObject();
            Dynamic[] dynamics;

            if (userId == null || page == null) {
                return new Result(ResultCode.PARAM_NOT_COMPLETE);
            }

            dynamics = dynamicMapper.getDynamic(userId, page);

            JSONArray dys = new JSONArray();

            for (Dynamic item : dynamics) {

                JSONObject dy = new JSONObject();

                DynamicReply[] replies = dynamicMapper.getReplyById(item.getDynamicId());

                dy.put("dynamic", item);
                dy.put("reply", replies);

                dys.add(dy);

            }

            res.put("dynamics", dys);
            return Result.SUCCESS(res);


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

    @GetMapping("api/dynamic/all")
    public Result getAllDynamic(String userId, Integer page) {
        try {

            JSONObject res = new JSONObject();
            Dynamic[] dynamics;

            if (userId == null || page == null) {
                return new Result(ResultCode.PARAM_NOT_COMPLETE);
            }

            dynamics = dynamicMapper.getAllDynamic(userId, page);

            JSONArray dys = new JSONArray();

            for (Dynamic item : dynamics) {

                JSONObject dy = new JSONObject();

                DynamicReply[] replies = dynamicMapper.getReplyById(item.getDynamicId());

                dy.put("dynamic", item);
                dy.put("reply", replies);

                dys.add(dy);

            }

            res.put("dynamics", dys);
            return Result.SUCCESS(res);


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }


    @PostMapping("api/dynamic")
    public Result postDynamic(@RequestBody JSONObject data) {
        try {

            String dynamicText = data.getString("dynamicText");
            String userId = data.getString("userId");

            dynamicMapper.insertDynamic(dynamicText, userId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

    @PostMapping("api/dynamic/delete")
    public Result removeDynamic(@RequestBody JSONObject data) {

        try {

            String dynamicId = data.getString("dynamicId");
            dynamicMapper.removeDynamic(dynamicId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("/api/dynamic/reply")
    public Result postReply(@RequestBody JSONObject data) {

        try {

            String dynamicId = data.getString("dynamicId");
            String replyText = data.getString("replyText");
            String replyTo = data.getString("replyTo");
            String replyFrom = data.getString("replyFrom");

            dynamicMapper.postReply(replyText, replyTo, replyFrom, dynamicId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @DeleteMapping("/api/dynamic/reply")
    public Result removeReply(@RequestBody JSONObject data) {

        try {

            String replyId = data.getString("replyId");

            dynamicMapper.removeReply(replyId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }


}

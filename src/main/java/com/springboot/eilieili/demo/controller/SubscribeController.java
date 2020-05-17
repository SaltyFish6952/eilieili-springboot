package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.mapper.SubscribeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class SubscribeController {

    @Autowired(required = false)
    SubscribeMapper subscribeMapper;

    @PostMapping("/api/subscribe")
    public Result addSubscribe(@RequestBody JSONObject data) {
        try {
            String subscribeUserId = data.getString("subscribeUserId");
            String publisherUserId = data.getString("publisherUserId");
            subscribeMapper.addSubscribe(subscribeUserId, publisherUserId);
            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

    @DeleteMapping("/api/subscribe")
    public Result removeSubscribe(@RequestBody JSONObject data) {
        try {
            String subscribeUserId = data.getString("subscribeUserId");
            String publisherUserId = data.getString("publisherUserId");
            subscribeMapper.removeSubscribe(subscribeUserId, publisherUserId);
            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

    @GetMapping("/api/subscribe")
    public Result getSubscribe(String subscribeUserId, String publisherUserId) {

        try {

            int count = subscribeMapper.getSubscribe(subscribeUserId, publisherUserId);
            JSONObject res = new JSONObject();
            res.put("isSubscribe", count == 1);

            return Result.SUCCESS(res);


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

}

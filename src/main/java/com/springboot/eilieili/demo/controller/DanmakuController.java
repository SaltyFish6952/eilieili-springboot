package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Danmaku;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.DanmakuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DanmakuController {

    @Autowired(required = false)
    private DanmakuMapper danmakuMapper;

    @JwtIgnore
    @GetMapping("/api/danmaku/v3")
    public Result getDanmaku(String id) {

        Danmaku[] danmakus = danmakuMapper.getDanmaku(id);
        JSONArray danmakusArray = new JSONArray();


        for (Danmaku item : danmakus) {

            JSONArray itemArray = new JSONArray();

            itemArray.add(item.getTime());
            itemArray.add(item.getType());
            itemArray.add(item.getColor());
            itemArray.add(item.getAuthor());
            itemArray.add(item.getText());

            danmakusArray.add(itemArray);
        }


        return Result.SUCCESS(danmakusArray);

    }


    @PostMapping("/api/danmaku/v3")
    public Result postDanmaku(@RequestBody JSONObject danmakuData) {

        try {
            danmakuMapper.postDanmaku(danmakuData.getString("text"),
                    danmakuData.getInteger("type"),
                    '#' + Integer.toHexString(danmakuData.getInteger("color")),
                    danmakuData.getString("time"),
                    danmakuData.getString("id"),
                    danmakuData.getString("author"));

        } catch (Exception e) {
            log.error("保存弹幕错误！Error：", e);
            return Result.FAIL("保存弹幕错误！");
        }

        return Result.SUCCESS();


    }

}

package com.springboot.eilieili.demo.mapper;

import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Danmaku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DanmakuMapper {

    public Danmaku[] getDanmaku(String videoId);

    public void postDanmaku(String text, Integer type, String color, String time, String id, String author);

}

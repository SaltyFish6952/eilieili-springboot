package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Video;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/16
 */
@Slf4j
@RestController
public class VideoController {

    @Autowired(required = false)
    VideoMapper videoMapper;

    @JwtIgnore
    @GetMapping("/api/video")
    public Result getVideo(Integer videoId) {

        try {


            Video video = videoMapper.getVideoInfoById(videoId);

            if (video == null) {
                return new Result(ResultCode.RESULT_DATA_NONE);
            }

            JSONObject result = new JSONObject();
            result.put("video", video);

            return Result.SUCCESS(result);


        } catch (Exception e) {
            log.error("获取video数据错误：{}", e.getMessage());
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

    }

    @JwtIgnore
    @GetMapping("/api/videos/ramdom")
    public Result getRamdomVideos() {

        Video[] videos = videoMapper.getVideosRamdom();

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("videos", videos);

        return Result.SUCCESS(result);


    }


    @JwtIgnore
    @GetMapping("/api/videos/user")
    public Result getUserVideos(Integer userId) {

        Video[] videos = videoMapper.getVideosByUserId(userId);

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("videos", videos);

        return Result.SUCCESS(result);

    }

    @JwtIgnore
    @GetMapping("/api/videos/sector")
    public Result getSectorVideos(Integer sectorId) {

        Video[] videos = videoMapper.getVideosBySectorId(sectorId);

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("videos", videos);

        return Result.SUCCESS(result);

    }

}

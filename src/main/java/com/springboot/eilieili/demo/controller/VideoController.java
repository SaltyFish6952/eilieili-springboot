package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Video;
import com.springboot.eilieili.demo.bean.VideoExtend;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.VideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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
    public Result getVideo(String videoId) {

        try {

            VideoExtend video = videoMapper.getVideoInfoById(videoId);

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
    @GetMapping("/api/videos/recommend")
    public Result getRecommendRandomVideos(String videoName) {

        if (videoName == null)
            return new Result(ResultCode.PARAM_IS_BLANK);

        //随机抽出一个字符
        Random random = new Random();
        int index = random.nextInt(videoName.length());

        String word = videoName.substring(index, index + 1);

        VideoExtend[] videos = videoMapper.getRecommendRandomVideos(word);

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        JSONObject result = new JSONObject();
        result.put("videos", videos);

        return Result.SUCCESS(result);

    }

    @JwtIgnore
    @GetMapping("/api/videos/random")
    public Result getRandomVideos(String sectorId) {

        if (sectorId == null) {

            Video[] videos = videoMapper.getVideosRandom();

            if (videos == null) {
                return new Result(ResultCode.RESULT_DATA_NONE);
            }

            JSONObject result = new JSONObject();
            result.put("videos", videos);

            return Result.SUCCESS(result);

        } else {

            Video[] videos = videoMapper.getVideosRandomBySectorId(sectorId);

            if (videos == null) {
                return new Result(ResultCode.RESULT_DATA_NONE);
            }

            JSONObject result = new JSONObject();
            result.put("videos", videos);

            return Result.SUCCESS(result);

        }


    }


    @JwtIgnore
    @GetMapping("/api/videos/sector")
    public Result getSectorVideos(String sectorId, Integer page) {


        if (sectorId == null && page == null)
            return new Result(ResultCode.PARAM_NOT_COMPLETE);

        Video[] videos = null;
        JSONObject result = new JSONObject();
        Integer videosCount = 0;

        if (page == null) {
            videos = videoMapper.getVideosBySectorId(sectorId);
            videosCount = videos.length;
        } else {
            videos = videoMapper.getVideosBySectorIdAndPage(sectorId, page);
            videosCount = videoMapper.getVideosCountBySectorId(sectorId);
        }

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        result.put("count", videosCount);
        result.put("videos", videos);


        return Result.SUCCESS(result);


    }


    @JwtIgnore
    @GetMapping("/api/videos/user")
    public Result getUserVideos(String userId, Integer page) {

        if (userId == null && page == null) {
            return new Result(ResultCode.PARAM_NOT_COMPLETE);
        }

        JSONObject result = new JSONObject();
        Integer videoCount = 0;
        Video[] videos = null;

        if (page == null) {
            videos = videoMapper.getVideosByUserId(userId);
            videoCount = videos.length;
        } else {
            videos = videoMapper.getVideosByUserIdAndPage(userId, page);
            videoCount = videoMapper.getVideosCountByUserId(userId);
        }

        if (videos == null) {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

        result.put("count", videoCount);
        result.put("videos", videos);

        return Result.SUCCESS(result);

    }

    @JwtIgnore
    @GetMapping("/api/video/search")
    public Result getVideoSearch(String query) {

        try {
            if (query == null) {
                return new Result(ResultCode.PARAM_IS_BLANK);
            }

            Video[] videos = videoMapper.getSearchVideo(query);
            JSONArray resArray = new JSONArray();

            for (int i = 0; i < videos.length; i++) {

                JSONObject item = new JSONObject();
                item.put("value", videos[i].getVideoName());
                item.put("id", videos[i].getVideoId());

                resArray.add(item);
            }

            JSONObject res = new JSONObject();
            res.put("videos", resArray);

            return Result.SUCCESS(res);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.SYSTEM_INNER_ERROR);
        }


    }

    @JwtIgnore
    @PostMapping("/api/video/viewed")
    public Result postVideoViewed(@RequestBody JSONObject data) {

        try {

            String videoId = data.getString("videoId");
            videoMapper.updateVideoViewTimes(videoId);
            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAIL(e.getMessage());
        }

    }

    @GetMapping("/api/video/like")
    public Result getUserVideoLike(String userId, String videoId) {

        try {

            if (userId == null || videoId == null) {
                return new Result(ResultCode.PARAM_NOT_COMPLETE);
            } else {

                Boolean isLike = (videoMapper.getLikeById(videoId, userId) != 0);

                JSONObject res = new JSONObject();
                res.put("isLike", isLike);

                return Result.SUCCESS(res);
            }

        } catch (Exception e) {
            e.printStackTrace();

            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("/api/video/like")
    public Result postUserVideoLike(@RequestBody JSONObject data) {

        try {

            String videoId = data.getString("videoId");
            String userId = data.getString("userId");
            Boolean isClickLike = data.getBoolean("isLike");

            if (isClickLike) {
                videoMapper.addUserLike(videoId, userId);
            } else {
                videoMapper.removeUserLike(videoId, userId);
            }

            return Result.SUCCESS();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }


}

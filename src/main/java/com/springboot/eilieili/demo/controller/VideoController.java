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

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.springboot.eilieili.demo.common.Constants.VIDEO_RESOURCE_PATH;
import static com.springboot.eilieili.demo.common.Constants.WEB_VIDEO_PATH;

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

            JSONObject result = new JSONObject();

            if (videoId == null) {
                Video[] videos = videoMapper.getAllVideos();

                if (videos == null) {
                    return new Result(ResultCode.RESULT_DATA_NONE);
                }
                result.put("videos", videos);

            } else {
                VideoExtend video = videoMapper.getVideoInfoById(videoId);
                if (video == null) {
                    return new Result(ResultCode.RESULT_DATA_NONE);
                }
                result.put("video", video);
            }

            return Result.SUCCESS(result);


        } catch (Exception e) {
            log.error("获取video数据错误：{}", e.getMessage());
            return new Result(ResultCode.RESULT_DATA_NONE);
        }

    }


    @JwtIgnore
    @PostMapping("/api/videos/recommend")
    public Result getRecommendRandomVideos(@RequestBody JSONObject data) {

        String videoName = data.getString("videoName");

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

            VideoExtend[] videos = videoMapper.getVideosRandomBySectorId(sectorId);

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
                item.put("videoPicPath", videos[i].getVideoPicPath());


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


    //favorite

    @GetMapping("/api/video/favorite")
    public Result getUserVideoFavorite(String userId, String videoId) {

        try {

            if (userId == null || videoId == null) {
                return new Result(ResultCode.PARAM_NOT_COMPLETE);
            } else {

                Boolean isFavorite = (videoMapper.getFavoriteById(videoId, userId) != 0);

                JSONObject res = new JSONObject();
                res.put("isFavorite", isFavorite);

                return Result.SUCCESS(res);
            }

        } catch (Exception e) {
            e.printStackTrace();

            return Result.FAIL(e.getMessage());
        }

    }


    @PostMapping("/api/video/favorite")
    public Result postUserVideoFavorite(@RequestBody JSONObject data) {

        try {

            String videoId = data.getString("videoId");
            String userId = data.getString("userId");
            Boolean isClickFavorite = data.getBoolean("isFavorite");

            if (isClickFavorite) {
                videoMapper.addUserFavorite(videoId, userId);
            } else {
                videoMapper.removeUserFavorite(videoId, userId);
            }

            return Result.SUCCESS();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @GetMapping("/api/video/download")
    public Result getVideoDownloadUrl(String videoId) {

        try {

            String uuid = videoMapper.getVideoUUID(videoId);
            String dashPath = VIDEO_RESOURCE_PATH + uuid + "/";
            String pattern = "^" + uuid + ".(flv|mov|mp4|avi)";
            Pattern r = Pattern.compile(pattern);
            JSONObject res = new JSONObject();

            File file = new File(dashPath);

            if (file.exists()) {
                File[] files = file.listFiles();

                if (files == null || files.length == 0) {
                    log.error("FAIL TO GET files");
                    return new Result(ResultCode.RESULT_DATA_NONE);
                }

                for (File insideFile : files) {

                    Matcher m = r.matcher(insideFile.getName());

                    if (m.find()) {
                        res.put("url", WEB_VIDEO_PATH + uuid + "/" + insideFile.getName());
                        return Result.SUCCESS(res);
                    }

                }
            }
            return new Result(ResultCode.RESULT_DATA_NONE);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @DeleteMapping("/api/video")
    public Result deleteVideo(@RequestBody JSONObject data) {
        try {

            videoMapper.removeVideo(data.getString("uuid"));

            deleteLocalDir(VIDEO_RESOURCE_PATH + data.getString("uuid") + "/");

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }


    private static void deleteLocalDir(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        deleteLocalDir(f.getPath());
                    }
                }
            }
            file.delete();
        }
        log.info("删除 " + dir + " 完成");
    }

    @JwtIgnore
    @GetMapping("/api/video/favorites")
    public Result getUserFavoriteVideos(String userId, Integer page) {

        try {

            if (userId == null || page == null) {
                return new Result(ResultCode.PARAM_NOT_COMPLETE);
            }

            Video[] videos = videoMapper.getUserFavoriteVideos(userId, page);

            JSONObject res = new JSONObject();
            res.put("videos", videos);

            return Result.SUCCESS(res);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PutMapping("/api/video")
    public Result updateVideo(@RequestBody JSONObject data) {

        try {

            String videoName = data.getString("videoName");
            String videoBrief = data.getString("videoBrief");
            String sectorId = data.getString("sectorId");
            String videoId = data.getString("videoId");

            videoMapper.updateVideo(videoName, videoBrief, sectorId, videoId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PutMapping("/api/video/check")
    public Result updateVideoCheck(@RequestBody JSONObject data) {

        try {

            Integer status = data.getInteger("status");
            String uuid = data.getString("uuid");
            videoMapper.updateVideoStatus(uuid, status);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }


}

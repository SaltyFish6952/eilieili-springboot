package com.springboot.eilieili.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.bean.Video;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.mapper.UserMapper;
import com.springboot.eilieili.demo.mapper.VideoMapper;
import com.springboot.eilieili.demo.videoTransfer.Transcode;
import com.springboot.eilieili.demo.videoTransfer.TranscodeTask;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static com.springboot.eilieili.demo.common.Constants.*;

@Slf4j
@RestController
public class UploadController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private VideoMapper videoMapper;

    @Autowired(required = false)
    private Transcode transcode;

    private final HashMap<String, String> filename_uuid = new HashMap<>();
    private final HashMap<String, String> picname_uuid = new HashMap<>();

    @PostMapping("/api/upload/video")
    public Result postVideo(@RequestParam(value = "videoFile") MultipartFile video,
                            @RequestParam("uuid") String uuid) {

        try {

            String fileName = video.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = UPLOAD_TMP_VIDEO_PATH; // 上传后的路径
            fileName = uuid + suffixName; // 新文件名
            File dest = new File(filePath + fileName);

            video.transferTo(dest);
            filename_uuid.put(uuid, fileName);

            return Result.SUCCESS();


        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("/api/upload/video/pic")
    public Result postVideoPic(@RequestParam("videoPic") MultipartFile pic, @RequestParam("uuid") String uuid) {

        try {

            String fileName = pic.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = PIC_RESOURCE_PATH + "video/"; // 上传后的路径
            fileName = uuid + suffixName; // 新文件名
            File dest = new File(filePath + fileName);

            picname_uuid.put(uuid, fileName);

            pic.transferTo(dest);


            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("api/upload/video/info")
    public Result postVideoInfo(@RequestBody JSONObject data) {

        String uuid = data.getString("uuid");
        String videoName = data.getString("videoName");
        String videoBrief = data.getString("videoBrief");
        String sectorId = data.getString("sectorId");
        String userId = data.getString("userId");

        try {

            videoMapper.postVideo(userId,
                    sectorId,
                    videoName,
                    WEB_VIDEO_PATH + uuid + "/dash/stream.mpd",
                    videoBrief,
                    WEB_VIDEO_PIC_PATH + picname_uuid.get(uuid),
                    0, uuid);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        transcode.newTask(new TranscodeTask(filename_uuid.get(uuid), videoMapper), uuid);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }


                }
            }).start();


            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }


    @PostMapping("/api/upload/user/pic")
    public Result postPic(@RequestParam(value = "file") MultipartFile file,
                          @RequestParam(value = "userId") String userId) {
        try {

            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = PIC_RESOURCE_PATH + "user/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);

            //remove old pic
            User user = userMapper.getUserById(userId);
            String oldPicPath = user.getUserPicPath();
            String oldPicName = oldPicPath.substring(oldPicPath.lastIndexOf('/') + 1);
            File oldPic = new File(filePath + oldPicName);
            if (oldPic.exists()) {
                if (!oldPic.delete())
                    log.error("error: delete file:" + oldPicName + " failed.");
            }

            //transfer and save to sql

            file.transferTo(dest);
            userMapper.updateUserPic(WEB_USER_PIC_PATH + fileName, oldPicPath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.SUCCESS();

    }


}

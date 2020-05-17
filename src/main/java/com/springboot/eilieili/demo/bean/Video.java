package com.springboot.eilieili.demo.bean;

import lombok.Data;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/14
 */
@Data
public class Video {
    private String videoId;
    private String userId;
    private String sectorId;
    private String videoName;
    private String videoPath;
    private String videoPicPath;
    private String videoBrief;
    private Integer videoViewTimes;
    private String maxQuality;
    private String videoTime;
    private String uuid;

}

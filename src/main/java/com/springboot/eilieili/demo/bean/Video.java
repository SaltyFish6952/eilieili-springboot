package com.springboot.eilieili.demo.bean;

import lombok.Data;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/14
 */
@Data
public class Video {
    private Integer videoId;
    private Integer userId;
    private Integer sectorId;
    private String videoName;
    private String videoPath;
    private String videoPicPath;
    private String videoBrief;

}

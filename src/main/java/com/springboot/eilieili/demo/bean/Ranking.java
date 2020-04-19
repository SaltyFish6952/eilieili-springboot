package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class Ranking {

    private String videoId;
    private String videoPicPath;
    private String videoName;
    private String userId;
    private String userName;
    private Integer likes;
    private Integer favorites;
    private Integer videoViewTimes;

}

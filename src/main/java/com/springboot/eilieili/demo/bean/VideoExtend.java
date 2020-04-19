package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class VideoExtend extends Video {
    private String userName;
    private String userPicPath;
    private Integer userLevel;
    private String sectorName;
    private Integer likes;
    private Integer favorites;

}

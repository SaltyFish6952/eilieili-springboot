package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class Comment {
    private String commentId;
    private String commentText;
    private String commentTime;
    private String userName;
    private String userId;
    private String userLevel;
    private String userPicPath;
    private String videoName;
}

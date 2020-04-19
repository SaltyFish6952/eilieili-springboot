package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class Comment {
    private String commentId;
    private String userId;
    private String commentText;
    private String userName;
    private String commentDate;
}

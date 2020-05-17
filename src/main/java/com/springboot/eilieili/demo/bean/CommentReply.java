package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class CommentReply {

    private String replyId;
    private String replyToId;
    private String replyToName;
    private String replyFromId;
    private String replyFromName;
    private String replyTime;
    private String replyText;

}

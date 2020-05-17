package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class DynamicReply {

    private String replyId;
    private String replyText;
    private String replyToName;
    private String replyToId;
    private String replyFromName;
    private String replyFromId;
    private String replyTime;

}

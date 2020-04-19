package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class Danmaku {
    String id;          //String danmakuId;
    String author;      //String danmakuUserName;
    String time;       //Integer danmakuTime;
    String text;        //String danmakuText;
    String color;       //String danmakuColor;
    Integer type;       //Integer danmakuType;
}

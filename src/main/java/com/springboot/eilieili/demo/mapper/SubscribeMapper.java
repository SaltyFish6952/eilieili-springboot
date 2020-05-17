package com.springboot.eilieili.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {

    public void addSubscribe(String subscriberUserId, String publisherUserId);

    public void removeSubscribe(String subscriberUserId, String publisherUserId);

    public Integer getSubscribe(String subscriberUserId, String publisherUserId);

}

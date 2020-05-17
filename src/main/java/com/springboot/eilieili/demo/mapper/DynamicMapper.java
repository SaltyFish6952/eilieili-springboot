package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Dynamic;
import com.springboot.eilieili.demo.bean.DynamicReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicMapper {

    public Dynamic[] getDynamic(String userId, Integer page);

    public Dynamic[] getAllDynamic(String userId, Integer page);

    public void insertDynamic(String dynamicText, String userId);

    public void removeDynamic(String dynamicId);

    public DynamicReply[] getReplyById(String dynamicId);

    public void postReply(String replyText, String replyTo, String replyFrom, String dynamicId);

    public void removeReply(String replyId);



}

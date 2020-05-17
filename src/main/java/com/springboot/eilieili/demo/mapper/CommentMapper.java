package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Comment;
import com.springboot.eilieili.demo.bean.CommentReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public Comment[] getCommentById(String videoId, Integer page);

    public Comment[] getComment();

    public void insertComment(String videoId, String userId, String commentText);

    public void removeComment(String commentId);

    public CommentReply[] getCommentReplyById(String commentId);

    public void postReply(String replyText, String replyTo, String replyFrom, String commentId);

    public void removeReply(String replyId);
}

package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Comment;
import com.springboot.eilieili.demo.bean.CommentReply;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CommentController {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @JwtIgnore
    @GetMapping("api/comments")
    public Result getComments(String videoId, Integer page) {

        try {

            JSONObject res = new JSONObject();
            Comment[] comments;

            if (videoId == null) {
                comments = commentMapper.getComment();
                res.put("comments", comments);
            } else {
                comments = commentMapper.getCommentById(videoId, page);

                JSONArray cms = new JSONArray();

                for (Comment item : comments) {

                    JSONObject cm = new JSONObject();

                    CommentReply[] replies = commentMapper.getCommentReplyById(item.getCommentId());

                    cm.put("comment", item);
                    cm.put("reply", replies);

                    cms.add(cm);

                }

                res.put("comments", cms);

            }


            return Result.SUCCESS(res);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("/api/comments")
    public Result postComment(@RequestBody JSONObject data) {

        try {

            String commentText = data.getString("commentText");
            String userId = data.getString("userId");
            String videoId = data.getString("videoId");

            commentMapper.insertComment(videoId, userId, commentText);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @DeleteMapping("/api/comments")
    public Result deleteComment(@RequestBody JSONObject data) {

        try {

            String commentId = data.getString("commentId");
            commentMapper.removeComment(commentId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @PostMapping("/api/comment/reply")
    public Result postReply(@RequestBody JSONObject data) {

        try {

            String commentId = data.getString("commentId");
            String replyText = data.getString("replyText");
            String replyTo = data.getString("replyTo");
            String replyFrom = data.getString("replyFrom");

            commentMapper.postReply(replyText, replyTo, replyFrom, commentId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }

    }

    @DeleteMapping("/api/comment/reply")
    public Result removeReply(@RequestBody JSONObject data) {

        try {

            String replyId = data.getString("replyId");

            commentMapper.removeReply(replyId);

            return Result.SUCCESS();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.FAIL(e.getMessage());
        }
    }

}

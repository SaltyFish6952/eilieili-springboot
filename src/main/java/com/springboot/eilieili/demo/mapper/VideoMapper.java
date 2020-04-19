package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Video;
import com.springboot.eilieili.demo.bean.VideoExtend;
import org.apache.ibatis.annotations.Mapper;
import sun.rmi.server.InactiveGroupException;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/16
 */
@Mapper
public interface VideoMapper {

    public VideoExtend getVideoInfoById(String videoId);

    public VideoExtend[] getRecommendRandomVideos(String keyword);

    public Video[] getVideosRandom();

    public Video[] getVideosRandomBySectorId(String sectorId);

    public Video[] getVideosBySectorId(String sectorId);

    public Integer getVideosCountBySectorId(String sectorId);

    public Video[] getVideosBySectorIdAndPage(String sectorId, Integer page);

    public Video[] getVideosByUserId(String userId);

    public Video[] getVideosByUserIdAndPage(String userId, Integer page);

    public Integer getVideosCountByUserId(String userId);

    public void postVideo(String userId, String sectorId, String videoName, String videoPath, String videoBrief,
                          String videoPicPath, Integer videoViewTimes, String uuid);

    public void postVideoQuality(String uuid, String quality);

    public Video[] getSearchVideo(String query);

    public void updateVideoViewTimes(String videoId);

    public Integer getLikeById(String videoId, String userId);

    public void addUserLike(String videoId, String userId);

    public void removeUserLike(String videoId, String userId);

}

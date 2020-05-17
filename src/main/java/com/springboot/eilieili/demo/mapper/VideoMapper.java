package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Video;
import com.springboot.eilieili.demo.bean.VideoExtend;
import com.springboot.eilieili.demo.bean.VideoTransCode;
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

    public Video[] getAllVideos();

    public VideoExtend[] getRecommendRandomVideos(String keyword);

    public Video[] getVideosRandom();

    public VideoExtend[] getVideosRandomBySectorId(String sectorId);

    public Video[] getVideosBySectorId(String sectorId);

    public Integer getVideosCountBySectorId(String sectorId);

    public VideoExtend[] getVideosBySectorIdAndPage(String sectorId, Integer page);

    public Video[] getVideosByUserId(String userId);

    public VideoExtend[] getVideosByUserIdAndPage(String userId, Integer page);

    public Integer getVideosCountByUserId(String userId);

    public void postVideo(String userId, String sectorId, String videoName, String videoPath, String videoBrief,
                          String videoPicPath, Integer videoViewTimes, String uuid);

    public void postVideoQuality(String uuid, String quality);

    public Video[] getSearchVideo(String query);

    public void updateVideoViewTimes(String videoId);

    public Integer getLikeById(String videoId, String userId);

    public void addUserLike(String videoId, String userId);

    public void removeUserLike(String videoId, String userId);

    public Integer getFavoriteById(String videoId, String userId);

    public void addUserFavorite(String videoId, String userId);

    public void removeUserFavorite(String videoId, String userId);

    public String getVideoUUID(String videoId);

    public VideoTransCode[] getVideoStatus(String userId);

    public void updateVideoStatus(String uuid, Integer status);

    public void removeVideo(String uuid);

    public Video[] getUserFavoriteVideos(String userId, Integer page);

    public void updateVideo(String videoName, String videoBrief, String sectorId, String videoId);

}

package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/16
 */
@Mapper
public interface VideoMapper {

    public Video getVideoInfoById(Integer videoId);
    public Video[] getVideosByUserId(Integer userId);
    public Video[] getVideosRamdom();
    public Video[] getVideos();
    public Video[] getVideosBySectorId(Integer sectorId);
}

package com.springboot.eilieili.demo.mapper;


import com.springboot.eilieili.demo.bean.Ranking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RankingMapper {

    public Ranking[] getRankingById(String sectorId);
    public Ranking[] getRanking();
    public Ranking[] getRankingByIdIndex(String sectorId);
}

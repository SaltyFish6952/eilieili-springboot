package com.springboot.eilieili.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.springboot.eilieili.demo.bean.Ranking;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.RankingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RankingController {

    @Autowired(required = false)
    private RankingMapper rankingMapper;

    @JwtIgnore
    @GetMapping("/api/ranking")
    public Result getRanking(String sectorId, Boolean isIndex) {

        Ranking[] rankings = null;

        if (sectorId == null) {

            rankings = rankingMapper.getRanking();

        } else {

            if (isIndex != null)
                rankings = rankingMapper.getRankingByIdIndex(sectorId);
            else
                rankings = rankingMapper.getRankingById(sectorId);
        }

        if (rankings.length != 0) {
            JSONObject result = new JSONObject();
            result.put("rankings", rankings);

            return Result.SUCCESS(result);
        } else {
            return new Result(ResultCode.RESULT_DATA_NONE);
        }
    }


}

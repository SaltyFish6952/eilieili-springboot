package com.springboot.eilieili.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.springboot.eilieili.demo.bean.Sector;
import com.springboot.eilieili.demo.common.response.Result;
import com.springboot.eilieili.demo.common.response.ResultCode;
import com.springboot.eilieili.demo.jwtAuthorization.JwtIgnore;
import com.springboot.eilieili.demo.mapper.SectorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/17
 */

@Slf4j
@RestController
public class SectorController {

    @Autowired(required = false)
    private SectorMapper sectorMapper;

//    @JwtIgnore
//    @GetMapping("/api/sectors")
//    public Result getSectors() {
//
//        Sector[] sectors = sectorMapper.getSectors();
//
//
//    }

    @JwtIgnore
    @GetMapping("/api/sector")
    public Result getSector(String sectorId) {

        if (sectorId == null) {
            Sector[] sectors = sectorMapper.getSectors();
            if (sectors == null) {
                return new Result(ResultCode.RESULT_DATA_NONE);
            }

            JSONObject result = new JSONObject();
            result.put("sectors", sectors);

            return Result.SUCCESS(result);
        } else {
            Sector sector = sectorMapper.getSectorById(sectorId);
            if (sector == null) {
                return new Result(ResultCode.RESULT_DATA_NONE);
            }
            JSONObject result = new JSONObject();
            result.put("sector", sector);

            return Result.SUCCESS(result);
        }


    }


}

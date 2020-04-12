package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.Sector;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/17
 */

@Mapper
public interface SectorMapper {

    public Sector[] getSectors();
    public Sector getSectorById(Integer sectorId);
}

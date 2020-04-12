package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/14
 */
@Mapper
public interface UserMapper {

    public User getUserByAccount(String userAccount);

    public String getUserPasswordByAccount(String userAccount);

//    public String getUserPassword(Integer userId);
//
//    public void insertUser(User user);
}

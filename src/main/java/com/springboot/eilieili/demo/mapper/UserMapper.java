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

    public User getUserById(String userId);

    public User getUserByAccount(String userAccount);

    public String getUserPasswordByAccount(String userAccount);

    public void updateUserPic(String newPicPath, String oldPicPath);

    public Integer checkUserName(String checkName,String userId);

    public Integer checkUserPassword(String userId, String checkPassword);

    public void updateUserName(String userId, String newName);

    public void updateUserPassword(String userId, String newPassword);

    public void updateVideoViewTimes(String videoId);

//    public String getUserPassword(Integer userId);
//
//    public void insertUser(User user);
}

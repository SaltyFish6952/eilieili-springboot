package com.springboot.eilieili.demo.mapper;

import com.springboot.eilieili.demo.bean.User;
import com.springboot.eilieili.demo.bean.UserExtend;
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

    public void updateUserPic(String newPicPath, String userId);

    public Integer checkUserName(String checkName,String userId);

    public Integer checkUserPassword(String userId, String checkPassword);

    public void updateUserName(String userId, String newName);

    public void updateUserPassword(String userId, String newPassword);

    public Integer checkNewUserName(String checkName);

    public Integer checkNewUserAccount(String userAccount);


    public UserExtend[] getAllUsers();
//
    public void insertUser(String userName, String userAccount, String userPassword);

    public void updateUser(String userId, String userName, String userAccount, String userPassword);

    public void deleteUser(String userId);
}

package com.springboot.eilieili.demo.bean;

import lombok.Data;
import lombok.With;


/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/14
 */
@Data

public class User {
    private String userId;
    private String userName;
    private Integer userLevel;
    private Integer userLevelProgress;
    private String userPicPath;
}

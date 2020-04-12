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
    private Integer userId;
    private String userName;
    private transient String userPassword;
    private Integer userLevel;
    private Integer userLevelProgress;
}

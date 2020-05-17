package com.springboot.eilieili.demo.bean;

import lombok.Data;

@Data
public class UserExtend extends User {
    private String userAccount;
    private String userPassword;
}

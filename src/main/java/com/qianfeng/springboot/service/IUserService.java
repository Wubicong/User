package com.qianfeng.springboot.service;

import com.qianfeng.springboot.bean.TbUser;
import com.qianfeng.springboot.po.UserInfo;
import org.springframework.stereotype.Service;


public interface IUserService {
    //用户登录
    void login(String username,String password) throws Exception;
    //验证手机号是否使用
    TbUser selectUserByTel(String phone);
    //验证用户名是否占用
    TbUser selectUserByName(String name);

    void insertUser(UserInfo userInfo);
}

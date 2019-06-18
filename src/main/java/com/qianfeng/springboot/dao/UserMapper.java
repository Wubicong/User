package com.qianfeng.springboot.dao;

import com.qianfeng.springboot.bean.TbUser;
import com.qianfeng.springboot.po.UserInfo;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    TbUser checkUsername(@Param("username") String username);
    TbUser checkEmail(@Param("email") String username);
    TbUser checkPhone(@Param("phone")String username);

    //查询手机号是否被使用
    TbUser selectUserByTel(@Param("phone") String phone);
    //查询用户名是否被使用
    TbUser selectUserByName(@Param("name")String name);

    void insertUser(@Param("userInfo") UserInfo userInfo);
}

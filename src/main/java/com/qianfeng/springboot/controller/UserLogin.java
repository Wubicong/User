package com.qianfeng.springboot.controller;

import com.qianfeng.springboot.utils.MD5Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login.do")
public class UserLogin extends BaseServlet {
    public void login(HttpServletRequest req, HttpServletResponse resp){
        String user_tel =req.getParameter("user_tel");
        String user_password=req.getParameter("user_pwd");
        String pwd= MD5Utils.encrypt(user_password);
        List<User> users=DBUtils.querry(User.class, "select*from tb_user where user_tel=? and user_password=?", user_tel,pwd);
        User user = users.get(0);
        if(users!=null &&users.size()>0){
            req.getSession().setAttribute("user", user);
            resp.getWriter().print("1");
        }else{
            resp.getWriter().print("0");
        }
    }
}

package com.qianfeng.springboot.controller;

import com.qianfeng.springboot.bean.JsonResult;
import com.qianfeng.springboot.bean.TbUser;
import com.qianfeng.springboot.po.UserInfo;
import com.qianfeng.springboot.service.IUserService;
import com.qianfeng.springboot.service.Impl.UserServiceImpl;
import com.qianfeng.springboot.utils.MD5Utils;
import com.qianfeng.springboot.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserServiceImpl userService;
    /*用户登录*/
    @RequestMapping("/login")
    public JsonResult login(String username, String password){
        JsonResult jsonResult = new JsonResult();
        try {
            userService.login(username,password);
            jsonResult.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setMsg("登录失败，账号或密码错误");
        }
        return jsonResult;
    }
    /*验证名称*/
    @RequestMapping("/selectName")
    public void selectUserByName(String username, HttpServletResponse resp){
        TbUser tbUser = userService.selectUserByName(username);
            try {
                if(tbUser==null) {
                    resp.getWriter().print("true");
                }else {
                    resp.getWriter().print("false");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }
    /*验证手机号*/
    @RequestMapping("/selectTel")
    public void selectUserByTel(String phone,HttpServletResponse resp){
        TbUser tbUser = userService.selectUserByTel(phone);
        try {
            if(tbUser==null) {
                resp.getWriter().print("true");
            }else {
                resp.getWriter().print("false");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /*注册*/
    @RequestMapping(value = "/register")
    public JsonResult register(@RequestBody RegisterVO registerVO, HttpSession session){
        JsonResult jsonResult = new JsonResult();
        String noteCode = registerVO.getNoteCode();

        String phone = registerVO.getPhone();
        String key="auction-register"+phone;
        //获取页面验证码
        try{
            String code = registerVO.getCode();
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            //判断session的验证码是否为空
            if (random == null) {
                jsonResult.setCode(0);
                jsonResult.setMsg("未知错误，请联系管理员");
                return jsonResult;
            }
            //判断验证码正确
            if (!random.equals(code)) {
                jsonResult.setCode(0);
                jsonResult.setMsg("验证码输入错误");
                return jsonResult;
            }
          //判断短信验证码
            if(redisTemplate.hasKey(key)){
                String redisCode =redisTemplate.boundValueOps(key).get();
                if(noteCode.equals(redisCode)){
                    //将用户信息插入数据库
                    UserInfo userInfo = new UserInfo();
                    String password = registerVO.getPassword();
                    String md5 = MD5Utils.md5(password, registerVO.getUsername());
                    userInfo.setPhone(phone);
                    userInfo.setUser_name(registerVO.getUsername());
                    userInfo.setUser_password(md5);
                    userService.insertUser(userInfo);
                    jsonResult.setCode(1);
                    jsonResult.setMsg("注册成功");
                }else {
                    jsonResult.setCode(0);
                    jsonResult.setMsg("手机验证码错误");
                    return jsonResult;
                }
            }
        }catch (Exception e){
            return jsonResult;
        }
        return jsonResult;
    }
}

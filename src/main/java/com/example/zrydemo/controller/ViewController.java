package com.example.zrydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.zrydemo.config.AuthToken;
import com.example.zrydemo.dto.TestDto;
import com.example.zrydemo.entity.UserInfo;
import com.example.zrydemo.mapper.UserInfoMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class ViewController {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @RequestMapping("/test")
    public String login(Model model) {
        model.addAttribute("back", "后台");
        List<TestDto> list = new ArrayList<>();
        TestDto dto = new TestDto();
        dto.setPwd("1");
        dto.setUserName("u");
        list.add(dto);
        list.add(new TestDto("test2", "pwd2"));
        model.addAttribute("list", list);
        model.addAttribute("rememberme", true);
        model.addAttribute("swt", "admin");
        return "test";
    }

    @RequestMapping("/login")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        model.addAttribute("img", "/img/hs.jpg");
        String str = "http://10.147.118.38:7002/4e725acb/api/queryb4cc967f8a16cbcc4c939831014c939f59860001_030000-4909_00076#1655954583";
        String ba = new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
        Base64.Encoder encoder = Base64.getEncoder();
        String two = encoder.encodeToString(str.getBytes(StandardCharsets.UTF_8));
        System.out.println("base64==" + ba);
        System.out.println("base64==" + two);
        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec("AccessSecret".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            byte[] hash = sha256_HMAC.doFinal(ba.getBytes("utf-8"));
            String encodeStr = byte2Hex(hash).toUpperCase();//new String(Base64.getEncoder().encode(hash));
            String result = Base64.getEncoder().encodeToString(encodeStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return modelAndView;
    }

    private String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    @RequestMapping("/webLogin")
//    @ResponseBody
    public String webLogin(UserInfo dto) {
        Subject subject = SecurityUtils.getSubject();
        try {
            System.out.println(JSONObject.toJSONString(dto));
            SimpleHash sh = new SimpleHash("MD5", dto.getPassword(), "xbNIxrQfn6COSYn1/GdloA==", 2);
            String hashedPwd = sh.toHex();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(dto.getUsername(), dto.getPassword());
            subject.login(usernamePasswordToken);
//            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("username", dto.getUsername());
//            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
//            Assert.notNull(userInfo, "无此用户!");
//            queryWrapper.eq("password", hashedPwd);
//            userInfo = userInfoMapper.selectOne(queryWrapper);
//            Assert.notNull(userInfo, "用户或密码错误!");
//
//            // todo , 获取token，并将token和用户id保存，myreal根据token查找对应用户信息
//            String sessionId = (String) subject.getSession().getId();
//            UpdateWrapper<UserInfo> wrapper = new UpdateWrapper<>();
//            userInfoMapper.update(null, wrapper.lambda().set(UserInfo::getToken, sessionId).eq(UserInfo::getUsername, dto.getUsername()));

//            new AuthToken(sessionId);
//            SessionKey key = new WebSessionKey(sessionID,request,response);
//            SecurityUtils.getSecurityManager().getSession("");
            return "test";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }


    //    @RequiresAuthentication
    @GetMapping("/logout")
    public String logout() {
        //在这里执行退出系统前需要清空的数据
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        System.out.println("退出登录成功");
        return "logout";
    }

    @RequestMapping("/tindex")
    public String tadd(Model model) {
        TestDto dto = new TestDto();
        dto.setUserName("a");
        dto.setPwd("tb");
        model.addAttribute("dto", dto);
        model.addAttribute("dtos", "testdto");
        return "login";
    }

    @RequestMapping("/403")
    public String back() {
        return "403";
    }

}

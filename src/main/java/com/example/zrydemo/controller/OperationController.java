package com.example.zrydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.zrydemo.dto.TestDto;
import com.example.zrydemo.entity.UserInfo;
import com.example.zrydemo.mapper.UserInfoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class OperationController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/save")
    @ResponseBody
    public void save(Model model, TestDto dto, HttpServletResponse response) {
        String json = JSONObject.toJSONString(dto);
        System.out.println(json);
        model.addAttribute("test", "df");
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(json);
        out.flush();
        out.close();
    }

    @PostMapping("/ajaxSave")
    @ResponseBody
    @RequiresPermissions({"userInfo:add"}) // 权限管理.
    public String save(Model model, TestDto dto) throws JsonProcessingException {
        String json = JSONObject.toJSONString(dto);
        System.out.println(json + "======ajaxSave");
//        String[] a=new String[2];
//        System.out.println(a[3]);
//        int ab=9/0;
        TestDto testDto = new TestDto();
        testDto.setUserName("dfsdd");
        testDto.setPwd("qps");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(testDto) + "======ajaxSave1");
        model.addAttribute("test", "df");
        return json;
    }

    @RequestMapping("/regedit")
    @ResponseBody
    public String regedit(UserInfo dto) throws JsonProcessingException {
        dto.setName("管理员");
        SimpleHash sh = new SimpleHash("MD5", dto.getPassword(), dto.getSalt(), 2);
        String hashedPwd = sh.toHex();
        dto.setPassword(hashedPwd);
        userInfoMapper.insert(dto);
        return "success";
    }


}

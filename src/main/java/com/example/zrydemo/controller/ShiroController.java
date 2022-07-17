package com.example.zrydemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @RequestMapping("/test")
    public String test() {
//        SecurityManager securityManager=SecurityManager.clas;
//        SecurityUtils.setSecurityManager((SecurityManager) securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "";
    }
}

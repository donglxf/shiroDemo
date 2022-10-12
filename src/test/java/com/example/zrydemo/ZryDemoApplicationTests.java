package com.example.zrydemo;

import com.alibaba.fastjson.JSONObject;
import com.example.zrydemo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.soap.SOAPBinding;

@SpringBootTest
@Slf4j
class ZryDemoApplicationTests {

    @Test
    void contextLoads() {
        UserInfo userInfo=new UserInfo();
        userInfo.setToken("dasdf");
        userInfo.setSalt("dfs");
        log.info("{}",userInfo);
        String str="{\"token\":\"sdfsf\",\"name\":\"sdf\"}";
        UserInfo is= JSONObject.parseObject(str,UserInfo.class);
        log.info("{}",is);
    }

}

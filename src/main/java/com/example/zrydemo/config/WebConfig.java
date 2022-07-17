package com.example.zrydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
////        registry.addResourceHandler("/js/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");
//        registry.addResourceHandler("/image/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/img/");
////        super.addResourceHandlers(registry);
//    }

//    @Override
    public void addCorsMappings_(CorsRegistry registry){
        CorsRegistration cors = registry.addMapping("/**");
        // 可访问的外部域
        cors.allowedOrigins("*");
        // 支持跨域用户凭证
//        cors.allowCredentials(true);
//        cors.allowedOriginPatterns("*");
        // 设置 header 能携带的信息
        cors.allowedHeaders("*");
        // 支持跨域的请求方法
        cors.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        // 设置跨域过期时间，单位为秒
        cors.maxAge(3600);
    }

    // 简写形式
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

}

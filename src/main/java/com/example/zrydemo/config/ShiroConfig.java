package com.example.zrydemo.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager manager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(manager);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("auth", new ShiroFilter());
//        filters.put("client", new ShiroFilter());
        filter.setFilters(filters);

        Map<String, String> fileterMap = new LinkedHashMap<String, String>();
        fileterMap.put("/js/**", "anon");
        fileterMap.put("/css/**", "anon");
        fileterMap.put("/img/**", "anon");
        fileterMap.put("/webLogin", "anon");
        fileterMap.put("/login", "anon");
        fileterMap.put("/regedit", "anon");
        fileterMap.put("/logout", "logout");
        fileterMap.put("/**", "auth");
//        fileterMap.put("/**", "user");
//        fileterMap.put("/**", "client");

        //  下面两行为单机配置,前后端分离模式下 可取消/保留 下2行，不受影响
//        filter.setLoginUrl("/webLogin");
//        filter.setSuccessUrl("/test");

//        filter.setUnauthorizedUrl("/403");
        filter.setFilterChainDefinitionMap(fileterMap);

        return filter;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理
//        securityManager.setSessionManager(setSessionManager);
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        return redisCacheManager;
    }

    @Bean
    public MyReal myShiroRealm() {
        MyReal myShiroRealm = new MyReal();
        myShiroRealm.setAuthorizationCachingEnabled(false);
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        BigScreenMatcher hashedCredentialsMatcher = new BigScreenMatcher();
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5"); // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2); // 散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    @Bean("myFilter")
//    public ShiroFilter shiroFilter() {
//        return new ShiroFilter();
//    }

}

package com.example.zrydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.zrydemo.entity.UserInfo;
import com.example.zrydemo.mapper.UserInfoMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MyReal extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(MyReal.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:add");
        return permissions;
    }

    /**
     * 模拟从数据库中获取角色数据
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo() called");
        UserInfo userName = (UserInfo) principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        List<UserInfo> userInfo = userInfoMapper.getPremissByUserName(userName.getUsername());
        userInfo.stream().forEach(item -> {
            roles.add(item.getRoleName());
            permissions.add(item.getPermissName());
        });
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo() called");
//        UsernamePasswordToken tokens = (UsernamePasswordToken) authenticationToken;
//        String username = tokens.getUsername();
//        String dusername = (String) tokens.getPrincipal();

        String tokens = (String) authenticationToken.getPrincipal();
        System.out.println("=================" + tokens);
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.or(). eq("username", tokens).or().eq("token", tokens);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        if (null == userInfo) {
            return null;
        }
        SimpleAuthenticationInfo token = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(),  ByteSource.Util.bytes(userInfo.getSalt())
                , this.getName());

        // 前后端分离模式
        // todo, 根据token查询用户信息，token在登陆方法已经保存至数据库表


//        AuthToken authToken = (AuthToken) authenticationToken;
//        String tokens = (String) authToken.getCredentials();

        return token;
    }

}

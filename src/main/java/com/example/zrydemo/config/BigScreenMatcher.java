package com.example.zrydemo.config;

import com.example.zrydemo.entity.UserInfo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

@Component
public class BigScreenMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        PasswordHelper passwordHelper = new PasswordHelper();
        UserInfo userInfo=(UserInfo)info.getPrincipals().getPrimaryPrincipal();
        AuthToken bigScreenToken = (AuthToken) token;
//        SimpleHash sh = new SimpleHash("MD5", dto.getPassword(), dto.getSalt(), 2);
        return bigScreenToken.getCredentials().equals(userInfo.getToken());
//        return info.getCredentials().toString().equals(passwordHelper.encryptPassword(bigScreenToken.getPassword(), bigScreenToken.getSalt()));
    }
}

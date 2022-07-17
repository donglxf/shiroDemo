package com.example.zrydemo.config;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zrydemo.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//@Component
public class ShiroFilter extends FormAuthenticationFilter{// AuthenticatingFilter {


//    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//        WebUtils.issueRedirect(request, response, "/test", null, true);
//    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        System.out.println("Fileter called");
        if (!StringUtils.isEmpty(getSuccessUrl())) {
            // getSession(false)：如果当前session为null,则返回null,而不是创建一个新的session
            Session session = subject.getSession(false);
            if (session != null) {
                session.removeAttribute("shiroSavedRequest");
            }
        }
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 单机配置，前后端分离可不用配置
     * 以下配置通过获取token来校验是否登陆，后续除登陆url不携带token，其他都需要携带token
      */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        System.out.println("isAccessAllowed() called");
//        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("onAccessDenied() called");
//        String token = TokenUtil.getRequestToken((HttpServletRequest) request);
//        if (StringUtils.isEmpty(token)) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
//            httpResponse.setCharacterEncoding("UTF-8");
//            Map<String, Object> result = new HashMap<>();
//            result.put("status", 403);
//            result.put("msg", "请先登录");
//            String json = JSONObject.toJSONString(request);
//            httpResponse.getWriter().print(json);
//            return false;
//        }
//        return super.executeLogin(request, response);
//    }
//
//    @Override
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
////        //获取请求token
//        System.out.println("createToken() called");
//        String token = TokenUtil.getRequestToken((HttpServletRequest) request);
//        return new AuthToken(token);
//    }

}

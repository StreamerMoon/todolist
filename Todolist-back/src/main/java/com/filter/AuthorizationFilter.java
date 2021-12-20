package com.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class AuthorizationFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("拦截到用户请求，正在校验用户登录情况。。。。");

        //注意下转型request对象。ServletRequest中没有获取头部信息的方法
        HttpServletRequest req = (HttpServletRequest) request;

        //获取头部信息中的token
        String token = req.getHeader("Authorization");

        System.out.println("用户token为：" + token);

        if (token == null || "".equals(token)) {//用户未登录
            System.out.println("用户未登录");
            request.setAttribute("verification result", "not logged in");
            chain.doFilter(request, response);
            return;
        }
        //获取session
        HttpSession session = req.getSession();
        //从session中获取服务端存储token
        String tokenServer = (String) session.getAttribute("token");
        if (!token.equals(tokenServer)) {//如果请求头中token与服务端token失配
            System.out.println("token错误或超时");
            request.setAttribute("verification result", "wrong token");
            chain.doFilter(request, response);
            return;
        }

        /*用户token匹配成功*/

        request.setAttribute("verification result", "logged in");
        chain.doFilter(request, response);
    }
}


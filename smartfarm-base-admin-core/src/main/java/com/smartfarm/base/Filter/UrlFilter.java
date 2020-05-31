package com.smartfarm.base.Filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "UrlFilter", urlPatterns = "/**")
public class UrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //为了兼容旧系统的动态后缀请求，这里进行截取处理
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        System.out.println("requestURI:"+requestURI);
        if (requestURI.endsWith("htm")) {
            String newURI=requestURI.substring(0,requestURI.lastIndexOf("."));
//            String newURI=requestURI.substring(0,requestURI.lastIndexOf("/"))+"/pswUpdate";  //经测试，两种方式配置的filter没有起作用
            req.getRequestDispatcher(newURI).forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}

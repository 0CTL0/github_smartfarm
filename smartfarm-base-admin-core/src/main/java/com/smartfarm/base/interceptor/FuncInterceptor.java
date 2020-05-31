package com.smartfarm.base.interceptor;


import com.smartfarm.base.util.SessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String reg = "/admin/(.*)\\.htm";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(httpServletRequest.getRequestURI());
        if (m.find()) {
        	if(httpServletRequest.getRequestURI().indexOf("login") > -1) {

        		return true;
        	}
            String func = httpServletRequest.getRequestURI();
            String funcList = (String) SessionUtil.getAttribute(httpServletRequest, SessionUtil.FUNCTION_LIST);
            if (funcList != null && !funcList.contains(func)) {
                httpServletResponse.sendError(403);
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

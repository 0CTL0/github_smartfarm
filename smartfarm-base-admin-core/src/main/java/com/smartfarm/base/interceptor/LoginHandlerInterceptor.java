package com.smartfarm.base.interceptor;


import com.smartfarm.base.util.SessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
	    
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {  
    }  
  
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
  
    }  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
    	 //获取请求的URL  
        String url = request.getRequestURI();  
        if(url.indexOf("/login.htm")>=0){  
            return true;  
        }
        //获取Session  
        Object userId = SessionUtil.getAttribute(request, SessionUtil.ADMIN_ID);
        if(userId == null){  
        	 response.sendRedirect("/index.html");
        }
        
        
        //获取用户权限，遍历时对比访问的URL
//      List<AdminPowerVo> powerList=(List<AdminPowerVo>) SessionUtil.getAttribute(request, SessionUtil.WH_ADMIN_USER_ROLE);
//       for(AdminPowerVo power : powerList){
//        	if(url.equals(power.getUrl()))
//        		return true;
//        }
       
       /* request.getRequestDispatcher("/adminUser/login.htm").forward(request,arg1); */
        return true;  
    }  

}

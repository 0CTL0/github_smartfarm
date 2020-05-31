package com.smartfarm.base.config.web;

import com.smartfarm.base.Filter.UrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.FilterRegistration;

@Component
@Configuration
public class FilterConfig {

    public FilterRegistrationBean registFilter(){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(new UrlFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("UrlFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}

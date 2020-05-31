package com;

import com.smartfarm.base.monitor.core.service.EventService;
import com.smartfarm.base.shop.core.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

@ServletComponentScan
@SpringBootApplication
public class SmartfarmAdminWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartfarmAdminWebappApplication.class, args);
    }

    @Bean
    public HessianProxyFactoryBean orderHessianClient(){
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:8099/orderService.htm");
        factoryBean.setServiceInterface(OrderService.class);
        return factoryBean;
    }


    @Bean
    public HessianProxyFactoryBean eventHessianClient(){
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:8099/eventService.htm");
        factoryBean.setServiceInterface(EventService.class);
        return factoryBean;
    }
}

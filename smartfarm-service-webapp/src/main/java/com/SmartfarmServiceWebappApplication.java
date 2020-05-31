package com;

import com.smartfarm.base.shop.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@SpringBootApplication
public class SmartfarmServiceWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartfarmServiceWebappApplication.class, args);
    }


    @Autowired
    private OrderService orderService;

    @Bean(name = "/hello/world/service")
    public HessianServiceExporter accountService(){
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(orderService);
        exporter.setServiceInterface(OrderService.class);
        return exporter;
    }

}

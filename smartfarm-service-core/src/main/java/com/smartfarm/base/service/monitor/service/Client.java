package com.smartfarm.base.service.monitor.service;


import com.smartfarm.base.monitor.core.entity.GatewayNode;
import com.smartfarm.base.monitor.core.manager.GatewayNodeManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class Client {
	private static Logger log = Logger.getLogger(Client.class);
    public static final String HOST="tcp://112.74.17.243:1883";
    private static final String clientid="service4";
    
    public static MqttClient clients;
    private MqttConnectOptions options;
    private String userName = "user";
    private String passWord = "user";
    
    @Autowired
    private  PushCallback pushCallback;
    @Autowired
    private GatewayNodeManager gatewayNodeManager;
    
    
    public void start() {
        try {
        	clients = new MqttClient(HOST, clientid);
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
             // 设置超时时间 单位为秒  
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制  
            options.setKeepAliveInterval(10);
            
            clients.setCallback(pushCallback);
            clients.connect(options);
            List<GatewayNode> list = gatewayNodeManager.queryAllGatewayNode();
            if(list.size() > 0) {
            	for(GatewayNode gatewayNode:list) {
            		int []Qos = {1};
                    String [] topic1 = {"stds/up/CL/" + gatewayNode.getProductCode()};
                    clients.subscribe(topic1,Qos);
                    log.info("订阅" + topic1.toString());
            	}
            }
            int []Qos = {1};
            String [] topic1 = {"message"};
            clients.subscribe(topic1,Qos);
            log.info("订阅" + topic1);
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}

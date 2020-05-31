package com.smartfarm.base.service.monitor.service;

import com.alibaba.fastjson.JSONObject;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**  
 * 发布消息的回调类  
 *   
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。  
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。  
 * 必须在回调类中实现三个方法：  
 *   
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。  
 *   
 *  public void connectionLost(Throwable cause)在断开连接时调用。  
 *   
 *  public void deliveryComplete(MqttDeliveryToken token))  
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。  
 *  由 MqttClient.connect 激活此回调。  
 *   
 */
public class PushCallback implements MqttCallback {  
	private static Logger log = Logger.getLogger(PushCallback.class);
	@Autowired
	private MonitorRealDataManager monitorRealDataManager;
	
	
    public void connectionLost(Throwable cause) {
    	log.info("连接断开，可以重连！");
    }
 
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        System.out.println("deliveryComplete -- "+token.isComplete());
        
    }
 
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        // TODO Auto-generated method stub
    	log.info("接收消息主题 : "+topic + ",接收消息 :"+ new String(msg.getPayload()));
        
        try {
        	if("message".equals(topic)) {
        		String payLoad = new String(msg.getPayload());
        		JSONObject json = JSONObject.parseObject(payLoad);
        		Integer type = (Integer)json.get("type");
        		if(type == 1) {//订阅
        			String code = (String)json.get("code");
        			int []Qos = {1};
                    String [] topic1 = {"stds/up/CL/" + code};
                    Client.clients.subscribe(topic1,Qos);
                    log.info("订阅" + topic1.toString());
        		}else if(type == 2) {//发消息
        			String code = (String)json.get("code");
                    MqttMessage message = new MqttMessage();
                    message.setQos(1);
                    message.setRetained(true);
                    String text = (String)json.get("text");
                    message.setPayload(text.getBytes());
                    Client.clients.publish("stds/up/CL/" + code, message);
                    log.info("发送消息：" + text + ",主题：stds/up/CL/" + code);
        		}
        		
        	}else {
        		monitorRealDataManager.getData(topic, new String(msg.getPayload()));
        	}
        	
		} catch (Exception e) {
			log.error("读取信息出错",e);
		}
        
    }

}

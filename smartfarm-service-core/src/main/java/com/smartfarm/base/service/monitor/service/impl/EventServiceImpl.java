package com.smartfarm.base.service.monitor.service.impl;

import com.smartfarm.base.monitor.core.entity.MonitorControlSet;
import com.smartfarm.base.monitor.core.manager.MonitorControlSetManager;
import com.smartfarm.base.monitor.core.service.EventService;
import com.smartfarm.base.service.monitor.quartz.InitQuartzJob;
import com.smartfarm.base.service.monitor.quartz.ScheduleJob;
import com.smartfarm.base.service.monitor.service.Client;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventServiceImpl implements EventService {
	protected static Logger log = Logger.getLogger(EventServiceImpl.class);
	
	@Autowired
	private MonitorControlSetManager monitorControlSetManager;

	public void sendProductMsg(String topic, String text) {
		log.info("收到硬件请求，主题：" + topic + ",内容：" + text);
		MqttMessage message = new MqttMessage();
        message.setQos(1);
        message.setRetained(true);
        message.setPayload(text.getBytes());
        try {
			Client.clients.publish(topic, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void subscribeTopic(String code) {
		int []Qos = {1};
        String [] topic1 = {"stds/up/CL/" + code};
        try {
			Client.clients.subscribe(topic1,Qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resetControlSet() {
		//关闭所有定时器
		try {
			InitQuartzJob.closeJob();
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		List<MonitorControlSet> setList = monitorControlSetManager.queryAllActiveTimeSet();
    	for(MonitorControlSet set:setList) {
    		String openStr = "0 " + replaceNum(set.getStartTime().substring(2, 4)) + " " + replaceNum(set.getStartTime().substring(0, 2))
    				+ " " + replaceNum(set.getStartDate().substring(6,8)) + "/1" + " " + replaceNum(set.getStartDate().substring(4,6)) 
    				+ " ? " + replaceNum(set.getStartDate().substring(0,4));
    		String closeStr = "0 " + replaceNum(set.getEndTime().substring(2, 4)) + " " + replaceNum(set.getEndTime().substring(0, 2))
    				+ " " + replaceNum(set.getStartDate().substring(6,8)) + "/1" + " " + replaceNum(set.getStartDate().substring(4,6)) 
    				+ " ? " + replaceNum(set.getStartDate().substring(0,4));
    		
    		ScheduleJob job = new ScheduleJob();
    		job.setJobId(set.getId() + "open-monitorControl");
        	job.setJobGroup("monitorControl"); // 任务组
        	job.setJobName(set.getId() + "open");// 任务名称
        	job.setCronExpression(openStr);
        	job.setBeanClass("com.smartfarm.base.service.monitor.timer.ControlSetJob");// 一个以所给名字注册的bean的实例
        	job.setMethodName("openControl");
        	Map<String, Object> map = new HashMap<String, Object>();
    		map.put("id", set.getId());
    		job.setMap(map);
    		try {
				InitQuartzJob.addJob(job);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
    		
    		ScheduleJob job1 = new ScheduleJob();
    		job1.setJobId(set.getId() + "close-monitorControl");
    		job1.setJobGroup("monitorControl"); // 任务组
    		job1.setJobName(set.getId() + "close");// 任务名称
    		job1.setCronExpression(closeStr);
    		job1.setBeanClass("com.smartfarm.base.service.monitor.timer.ControlSetJob");// 一个以所给名字注册的bean的实例
    		job1.setMethodName("closeControl");
        	Map<String, Object> map1 = new HashMap<String, Object>();
    		map1.put("id", set.getId());
    		job1.setMap(map1);
    		try {
				InitQuartzJob.addJob(job1);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public static String replaceNum(String str) {
		String s = str.replaceAll("^(0+)", "");
		if("".equals(s)) {
			s = "0";
		}
		return s;
	}
}

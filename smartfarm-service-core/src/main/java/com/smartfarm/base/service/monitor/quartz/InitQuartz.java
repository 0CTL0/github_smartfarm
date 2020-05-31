package com.smartfarm.base.service.monitor.quartz;

import com.smartfarm.base.monitor.core.entity.MonitorControlSet;
import com.smartfarm.base.monitor.core.manager.MonitorControlSetManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitQuartz implements InitializingBean {
	private static Logger log = Logger.getLogger(InitQuartz.class);
	@Autowired
	private MonitorControlSetManager monitorControlSetManager;

	public void afterPropertiesSet() throws Exception {
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
    		InitQuartzJob.addJob(job);
    		
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
    		InitQuartzJob.addJob(job1);
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

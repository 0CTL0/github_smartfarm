package com.smartfarm.base.service.monitor.quartz;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	@SuppressWarnings("unchecked")
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class clazz = null;
		boolean flag = true;
		try {
			clazz = Class.forName(scheduleJob.getBeanClass());
			object = SpringUtils.getBean(clazz);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		if (object == null) {
			flag = false;
			log.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName(),java.util.Map.class);
		} catch (NoSuchMethodException e) {
			flag = false;
			e.printStackTrace();
			log.error("任务名称 = [" + scheduleJob.getJobName()
					+ "]未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (method != null) {
			try {
				
				method.invoke(object, scheduleJob.getMap());
			} catch (IllegalAccessException e) {
				flag = false;
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				flag = false;
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				flag = false;
				e.printStackTrace();
			}
		}
		if (flag) {
			System.out.println("任务名称 = [" + scheduleJob.getJobName()
					+ "]启动成功");
		}

	}
}
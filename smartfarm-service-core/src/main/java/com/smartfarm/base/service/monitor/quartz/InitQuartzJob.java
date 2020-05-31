package com.smartfarm.base.service.monitor.quartz;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class InitQuartzJob implements ApplicationContextAware {
	private static Logger log = Logger.getLogger(InitQuartzJob.class);
	
	private static ApplicationContext appCtx;
	public static SchedulerFactoryBean schedulerFactoryBean = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if (appCtx == null) {
	      appCtx = applicationContext;
	    }
	}
	
	public static void init() {
	    schedulerFactoryBean = (SchedulerFactoryBean) appCtx.getBean(SchedulerFactoryBean.class);
	}
	 
	 
	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void addJob(ScheduleJob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
 
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
 
		log.info("add job[name:" + job.getJobName() + "]");
		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = QuartzJobFactory.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withDescription(job.getJobId().toString()).withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	public static void closeJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals("monitorControl"))) {
			System.out.println("关闭" + jobKey.getName());
			scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
		}
	}
	
}

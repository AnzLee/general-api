/**
 * 定时任务工具类
 *
 * @author li.liangzhe
 * @create 2018-01-09 9:57
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.service.QuartzService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class QuartzServiceImpl implements QuartzService {
    private final static Log logger = LogFactory.getLog(QuartzServiceImpl.class);

    @Autowired
    Scheduler sched;

    public void startDelayTimeJob(String key, String group, int delayMillisecond, Class clz) throws SchedulerException {
        sched.start();
        long fTime = System.currentTimeMillis() + delayMillisecond;
        JobDetail job = newJob(clz).withIdentity(key, group).build();
        Trigger trigger = newTrigger().withIdentity(key, group).startAt(new Date(fTime))
                .withSchedule(simpleSchedule().withRepeatCount(0)).build();
        sched.scheduleJob(job, trigger);
    }

    public void startFixedDateJob(String key, String group, Date triggerStartTime, Class clz) throws SchedulerException {
        sched.start();
        JobDetail job = newJob(clz).withIdentity(key, group).build();
        Trigger trigger = newTrigger().withIdentity(key, group).startAt(triggerStartTime)
                .withSchedule(simpleSchedule().withRepeatCount(0)).build();
        sched.scheduleJob(job, trigger);
    }

    public void startCycleLimitedJob(String key, String group, int interval, int count, Class clz) throws SchedulerException {
        sched.start();
        JobDetail job = newJob(clz).withIdentity(key, group).build();
        Trigger trigger = newTrigger().withIdentity(key, group).startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(interval).withRepeatCount(count)).build();
        sched.scheduleJob(job, trigger);
    }

    public void startCronJob(String key, String group, String cron, Class clz) throws SchedulerException {
        sched.start();
        JobDetail job = newJob(clz).withIdentity(key, group).build();
        Trigger trigger = newTrigger().withIdentity(key, group).startNow().withSchedule(cronSchedule(cron)).build();
        sched.scheduleJob(job, trigger);
    }

    public void startCronJobWithData(String key, String group, String cron, Class clz, JobDataMap map) throws SchedulerException {
        startCronJobWithData(key, group, key, group, cron, clz, map);
    }

    public void startCronJobWithData(String jobName, String jobGroup, String triggerName, String triggerGroup,
                                            String cron, Class clz, JobDataMap map) throws SchedulerException {
        sched.start();
        JobDetail job = newJob(clz).withIdentity(jobName, jobGroup).setJobData(map).build();
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroup).startNow()
                .withSchedule(cronSchedule(cron)).build();
        sched.scheduleJob(job, trigger);
    }

    public void startCronJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron, Class clz) throws SchedulerException {
        sched.start();
        logger.info("schedId : " + sched.getSchedulerInstanceId() + ",schedName : " + sched.getSchedulerName() + ", " + sched.toString());
        JobDetail job = newJob(clz).withIdentity(jobName, jobGroup).build();
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroup).startNow()
                .withSchedule(cronSchedule(cron)).build();
        sched.scheduleJob(job, trigger);
    }

    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jk = new JobKey(jobName, jobGroup);
        for (String groupName : sched.getJobGroupNames()) {
            for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                if (jobKey.equals(jk)) {
                    sched.pauseJob(jk);
                    logger.info("[Stop] job : " + jobKey);
                }
            }
        }
    }

    public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jk = new JobKey(jobName, jobGroup);
        for (String groupName : sched.getJobGroupNames()) {
            for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                if (jobKey.equals(jk)) {
                    sched.deleteJob(jk);
                    logger.info("[Delete] job : " + jobKey);
                }
            }
        }
    }

    public void printJob() throws SchedulerException {
        logger.info("[Print] Current Scheduler Size : " + sched.getJobGroupNames().size());
        List<String> groupList = sched.getJobGroupNames();
        logger.info("[Print] Current Group Size : " + groupList.size());
        for (String groupName : groupList) {
            Set<JobKey> jobKeySet = sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
            logger.info("[Print] Current JOB Size : " + jobKeySet.size());
            for (JobKey jobKey : jobKeySet) {
                logger.info("[Print] Current JOB : " + jobKey);
            }
        }
    }
}

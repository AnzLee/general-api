/**
 * @author li.liangzhe
 * @create 2018-01-31 16:31
 **/
package com.anzlee.generalapi.service;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.util.Date;

public interface QuartzService {

    void startDelayTimeJob(String key, String group, int delayMillisecond, Class clz) throws SchedulerException;

    void startFixedDateJob(String key, String group, Date triggerStartTime, Class clz) throws SchedulerException;

    void startCycleLimitedJob(String key, String group, int interval, int count, Class clz) throws SchedulerException;

    void startCronJob(String key, String group, String cron, Class clz) throws SchedulerException;

    void startCronJobWithData(String key, String group, String cron, Class clz, JobDataMap map) throws SchedulerException;

    void startCronJobWithData(String jobName, String jobGroup, String triggerName, String triggerGroup,
                              String cron, Class clz, JobDataMap map) throws SchedulerException;

    void startCronJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron, Class clz) throws SchedulerException;

    void pauseJob(String jobName, String jobGroup) throws SchedulerException;

    void deleteJob(String jobName, String jobGroup) throws SchedulerException;

    void printJob() throws SchedulerException;
}

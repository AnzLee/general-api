/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.dao.TaskRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.job.BasicPushJob;
import com.anzlee.generalapi.service.APIService;
import com.anzlee.generalapi.service.QuartzService;
import com.anzlee.generalapi.service.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final static Log logger = LogFactory.getLog(TaskServiceImpl.class);

    @Autowired
    TaskRepositoty taskRepositoty;
    @Autowired
    APIService apiService;
    @Autowired
    QuartzService quartzService;

    private final String Group = "PUSH_JOB";

    @Override
    public Task findById(Long id) {
        return taskRepositoty.findOne(id);
    }

    @Override
    public Task findByName(String name) {
        return taskRepositoty.findByTaskName(name);
    }

    @Override
    @Transactional
    public Integer updateLastExTime(Long id, Date lastExTime){
        return taskRepositoty.updateLastExTime(id, lastExTime);
    }

    @Override
    @Transactional
    public Integer updateTaskStatus(Long id, Task.Status status) {
        return taskRepositoty.updateTaskStatus(id, status);
    }

    @Override
    public Task save(Task task) {
        return taskRepositoty.save(task);
    }

    @Override
    @Transactional(value="transactionManager", rollbackFor = Exception.class)
    public Task saveWithAPI(Task task, Long[] apis) {
        List<API> taskApis = new ArrayList<API>();
        for(Long api:apis){
            API taskApi = apiService.findById(api);
            taskApis.add(taskApi);
        }
        task.setTaskApi(taskApis);
        return save(task);
    }

    @Override
    public Page<Task> taskView(Pageable pageable){
        return taskRepositoty.findAll(pageable);
    }

    @Override
    public String taskView(Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page-1,limit);
        Page<Task> taskPage = taskRepositoty.findAll(pageable);
        JSONObject json = new JSONObject();
        json.fluentPut("code","0");
        json.fluentPut("count", taskPage.getTotalElements());
        json.fluentPut("data",taskPage.getContent());
        return json.toJSONString();
    }

    @Override
    public List<Task> findAllTask() {
        return taskRepositoty.findAll();
    }

    @Override
    public boolean quartzPush(Task task, String cron) {
        JobDataMap data =  new JobDataMap();
        data.put("taskId",task.getID());
        data.put("taskName",task.getTaskName());
        data.put("taskApis",task.getTaskApi());
        if(!cron.equals(task.getTaskExTime())){
            task.setTaskExTime(cron);
            task = taskRepositoty.save(task);
        }
        try {
            quartzService.startCronJobWithData(task.getTaskName(),Group,task.getTaskExTime(), BasicPushJob.class, data);
            task.setTaskStatus(Task.Status.Started);
            taskRepositoty.save(task);
            return true;
        } catch (SchedulerException e) {
            logger.error("", e);
            return false;
        }
    }

    @Override
    public boolean quartzPushStop(Task task) {
        if(task.getTaskStatus()!= Task.Status.Stop){
            try {
                quartzService.deleteJob(task.getTaskName(), Group);
                return true;
            } catch (SchedulerException e) {
                logger.error("", e);
            }
        }
        return false;
    }
}

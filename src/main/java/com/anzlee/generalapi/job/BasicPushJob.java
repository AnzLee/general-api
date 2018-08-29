/**
 * 基础推送定时任务
 *
 * @author li.liangzhe
 * @create 2018-01-09 9:35
 **/
package com.anzlee.generalapi.job;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.service.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class BasicPushJob implements Job {
    private final static Log logger = LogFactory.getLog(BasicPushJob.class);

    private Long taskId;
    private String taskName;
    private List<API> taskApis;

    private boolean isExSuccess = true;

    @Autowired
    TaskService taskService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        init(context);
        taskService.updateLastExTime(taskId, new Date());
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        HttpClient client = clientBuilder.build();
        try {
            for (API api : taskApis) {
                HttpResponse response = null;
                if (api.getApiMethod() == API.Method.get) {
                    HttpGet httpGet = new HttpGet(api.getApiProtocol()+"://"+api.getApiUrl());
                    if(API.Encode.UTF8 == api.getApiEncode()){
                        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    }
                    response = client.execute(httpGet);
                    logger.info("Execute Http Get Method");
                } else if (api.getApiMethod() == API.Method.post) {
                    HttpPost httpPost = new HttpPost(api.getApiUrl());
                    if(API.Encode.UTF8 == api.getApiEncode()){
                        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    }
                    logger.info("Execute Http Post Method");
                    response = client.execute(httpPost);
                }
                if(200 == response.getStatusLine().getStatusCode()){

                } else {
                    logger.error("网络请求错误");
                    throw new Exception("网络请求错误");
                }
            }
        } catch (HttpHostConnectException e) {
            logger.error("连接失败");
            isExSuccess = false;
        } catch (IOException e) {
            logger.error("IO错误");
            isExSuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            isExSuccess = false;
        } finally {
            if(isExSuccess){
                taskService.updateTaskStatus(taskId, Task.Status.Success);
            } else {
                taskService.updateTaskStatus(taskId, Task.Status.Failed);
            }
        }
    }

    public void init(JobExecutionContext context){
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        this.taskId = dataMap.getLong("taskId");
        this.taskName = dataMap.getString("taskName");
        this.taskApis = (List<API>) dataMap.get("taskApis");
    }
}


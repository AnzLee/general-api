/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.APIService;
import com.anzlee.generalapi.service.CoderService;
import com.anzlee.generalapi.service.TaskService;
import com.anzlee.generalapi.util.SpringManager;
import com.anzlee.generalapi.util.SwaggerManager;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    APIService apiService;
    @Autowired
    TaskService taskService;
    @Autowired
    CoderService coderService;
    @Autowired
    SwaggerManager swaggerManager;

    /**
     * 获取所有API
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String getAllTask(){
        JSONObject json = new JSONObject();
        json.fluentPut("task",taskService.findAllTask());
        return json.toJSONString();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String view(Integer page, Integer limit){
        if(page==null){
            page = 1;
        }
        if(limit==null){
            limit = 10;
        }
        return taskService.taskView(page, limit);
    }

    @RequestMapping(value = "/one",method = RequestMethod.GET)
    @ResponseBody
    public Task viewById(Long id){
        Task task = taskService.findById(id);
        return task;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message add(@ModelAttribute Task task){
        if(taskService.save(task)!=null){
            return SUCCESS_MESSAGE;
        }else {
            return ERROR_MESSAGE;
        }
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    @ResponseBody
    public Message run(@RequestParam(value = "id") Long id,@RequestParam(value = "cron") String cron){
        if(id==null || id==0){
            return  ERROR_MESSAGE;
        }
        Task task = taskService.findById(id);
        if(task.getTaskType()==Task.Type.PULL){
            if(task.getTaskApi().size()==0){
                return SUCCESS_MESSAGE;
            }
            for(API api: task.getTaskApi()){
                if(!coderService.genByAPIID(api.getID()))return ERROR_MESSAGE;
            }
            if(coderService.compiler()){
                for(API api: task.getTaskApi()) {
                    try {
                        coderService.loadClass(api.getApiName());
                        SpringManager.registerBean("_"+api.getApiName()+"Controller", "com.anzlee.generalapi.third._"+api.getApiName()+"Controller");
                        SpringManager.registerController("_"+api.getApiName()+"Controller");
                        swaggerManager.refreshSwagger();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ERROR_MESSAGE;
                    }
                }
                return SUCCESS_MESSAGE;
            } else {
                return ERROR_MESSAGE;
            }
        } else if(task.getTaskType()==Task.Type.PUSH){
            if(cron!=null && isValidExpression(cron)){
                if (taskService.quartzPush(task, cron)){
                    return SUCCESS_MESSAGE;
                }
            }
        }
        return ERROR_MESSAGE;
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ResponseBody
    public Message stop(@RequestParam(value = "id") Long id) {
        if(id==null || id==0){
            return  ERROR_MESSAGE;
        }
        Task task = taskService.findById(id);
        if(task.getTaskType()==Task.Type.PULL){
            for(API api: task.getTaskApi()){

            }
        } else if(task.getTaskType()==Task.Type.PUSH){
            if(taskService.quartzPushStop(task)){
                return SUCCESS_MESSAGE;
            }
        }
        return ERROR_MESSAGE;
    }

    //CRON表达式验证
    private boolean isValidExpression(final String cronExpression){
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (Exception e) {
            log.error("[TaskUtils.isValidExpression]:failed. throw ex:" , e);
        }
        return false;
    }
}

/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.APIService;
import com.anzlee.generalapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    APIService apiService;
    @Autowired
    TaskService taskService;


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
}

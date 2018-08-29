/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class APIController {

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    APIService apiService;

    /**
     * 获取所有API
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String getAllApi(){
        JSONObject json = new JSONObject();
        json.fluentPut("api",apiService.findAllApi());
        return json.toJSONString();
    }

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String view(Integer page, Integer limit){
        if(page==null){
            page = 1;
        }
        if(limit==null){
            limit = 10;
        }
        return apiService.apiView(page, limit);
    }

    /**
     * ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/one",method = RequestMethod.GET)
    @ResponseBody
    public API viewById(Long id){
        API api = apiService.findById(id);
        return api;
    }

    /**
     * 添加
     * @param api
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message add(@ModelAttribute API api, @RequestParam(name = "task") Long taskId){
        if(apiService.save(api, taskId)!=null){
            return SUCCESS_MESSAGE;
        }else {
            return ERROR_MESSAGE;
        }
    }
}

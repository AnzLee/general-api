/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class APIController {

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    APIService apiService;

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

    @RequestMapping(value = "/one",method = RequestMethod.GET)
    @ResponseBody
    public API viewById(Long id){
        API api = apiService.findById(id);
        return api;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message add(@ModelAttribute API api){
        if(apiService.save(api)!=null){
            return SUCCESS_MESSAGE;
        }else {
            return ERROR_MESSAGE;
        }
    }
}

/**
 * 动态代码测试接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.CoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/coder")
public class CoderController {

    @Autowired
    CoderService coderService;

    @Autowired
    ApplicationContext context;

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message genByAPIID(Long apiID){
        if(coderService.genByAPIID(apiID)){
            if(coderService.compiler()){
                return SUCCESS_MESSAGE;
            }
        }
        return ERROR_MESSAGE;
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public String loadClass(String apiName){
        coderService.loadClass(apiName);
        return "success";
    }

}

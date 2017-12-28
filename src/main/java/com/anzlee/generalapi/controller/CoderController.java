/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.CoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/coder")
public class CoderController {

    @Autowired
    CoderService coderService;

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

}

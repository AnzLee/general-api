/**
 * 用户控制层
 *
 * @author li.liangzhe
 * @create 2017-11-23 14:01
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.coder.loader.FileClassLoader;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Param;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class ThirdController {

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    APIService apiService;

    @RequestMapping(value = "/third/{className}", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Message handler(@PathVariable("className")String className,HttpServletRequest request){
        String wholeClassName = "com.anzlee.generalapi.third._"+className+"Controller";
        String methodName = "CommonMethod";
        API api = apiService.findByName(className);
        if(api.getApiParams().size()!=0){
            Object[] methodParamValues = new Object[api.getApiParams().size()];
            Class[] methodParams = new Class[api.getApiParams().size()];
            int i=0;
            for(Param param : api.getApiParams()){
                methodParamValues[i] = request.getParameter(param.getParamName());
                switch (param.getParamType()){
                    case INT: methodParams[i] = Integer.class;break;
                    case STRING: methodParams[i] = String.class;break;
                    case FLOAT: methodParams[i] = Float.class;break;
                    case BOOLEAN: methodParams[i] = Boolean.class;break;
                    case TIME: methodParams[i] = Date.class;break;
                    default: return ERROR_MESSAGE;
                }
                i++;
            }
            if(methodParams.length==0){
                return ERROR_MESSAGE;
            }
            if(FileClassLoader.loadClassAndInvokeMethod(wholeClassName, methodName, methodParamValues, methodParams)){
                return SUCCESS_MESSAGE;
            }
        } else {
            if(FileClassLoader.loadClassAndInvokeMethod(wholeClassName, methodName, null, null)){
                return SUCCESS_MESSAGE;
            }
        }
        return ERROR_MESSAGE;
    }
}
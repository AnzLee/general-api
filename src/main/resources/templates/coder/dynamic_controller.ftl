package com.anzlee.generalapi.third;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import com.anzlee.generalapi.third._${tableName}Entity;
import com.anzlee.generalapi.third._${tableName}Service;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.base.Page;
import com.anzlee.generalapi.base.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;

/**
* 描述：${tableName}控制层
* @author anzlee
* @date ${date}
*/
@Api(value="${tableName}",tags={"${tableName}"})
@Controller
@RequestMapping(value = "${tableName}")
public class _${tableName}Controller{

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @Autowired
    _${tableName}Service baseService;

    /**
     * 描述：_${tableName}详情方法
     *
     <#--<#list apiParams as apiParam>-->
     <#--* @param ${apiParam.paramName}  ${apiParam.paramValue}-->
     <#--</#list>-->
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    <#--@ApiImplicitParams({-->
    <#--<#list apiParams as apiParam>-->
    <#--@ApiImplicitParam(name="${apiParam.paramName}",value="${apiParam.paramValue}",dataType="${apiParam.paramType}", paramType = "query"),-->
    <#--</#list>-->
    <#--})-->
    public _${tableName}Entity find(@RequestParam("Id") Long id)throws Exception{
        return baseService.find(id);
    }

    /**
    * 描述：_${tableName}列表方法
    *
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Page<_${tableName}Entity> list()throws Exception{
        return baseService.findAll();
    }

    /**
    * 描述：_${tableName}新增方法
    *
    */
    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public _${tableName}Entity add(@RequestParam("${tableName}") _${tableName}Entity entity)throws Exception{
        return baseService.save(entity);
    }

    /**
    * 描述：_${tableName}修改方法
    *
    */
    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public _${tableName}Entity update(@RequestParam("${tableName}") _${tableName}Entity entity)throws Exception{
        return baseService.update(entity);
    }

    /**
    * 描述：_${tableName}删除方法
    *
    */
    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Message delete(@RequestParam("Id") Long id)throws Exception{
        baseService.delete(id);
        return SUCCESS_MESSAGE;
    }

    public void validate(){
        System.out.println("_${tableName}Controller has been Loaded");
    }
}

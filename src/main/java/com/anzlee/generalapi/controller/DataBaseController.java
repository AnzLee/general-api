/**
 * API接口
 *
 * @author li.liangzhe
 * @create 2017-12-04 17:34
 **/
package com.anzlee.generalapi.controller;

import com.alibaba.fastjson.JSON;
import com.anzlee.generalapi.entity.Database;
import com.anzlee.generalapi.pojo.Message;
import com.anzlee.generalapi.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/database")
public class DataBaseController {

    @Autowired DatabaseService databaseService;

    /** 错误消息 */
    protected static final Message ERROR_MESSAGE = Message.error("manager.message.error");

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("manager.message.success");

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String validDatabase(@ModelAttribute Database database,String msg){
        if(StringUtils.isEmpty(database.getDataPath())){
            msg = "{\"success\":false,\"msg\":\"null value\"}";
        } else {
            List<String> tList = databaseService.validDatabase(database);
            if (tList != null && tList.size() != 0) {
                msg = "{\"tables\":"+JSON.toJSONString(tList)+",\"success\":true}";
            } else {
                msg = "{\"success\":false,\"msg\":\"no table\"}";
            }
        }
        return msg;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String add(@ModelAttribute Database database){

        return "{\"abc\":123}";
    }

    @RequestMapping(value = "/fields", method = RequestMethod.POST)
    @ResponseBody
    public String getFields(String path, Database.Type type, String name, String tableName, String msg){
        if(StringUtils.isEmpty(path)){
            msg = "{\"success\":false,\"msg\":\"null value\"}";
        } else {
            List<String> cList = databaseService.getFields(path, type, name, tableName);
            if (cList != null && cList.size() != 0) {
                msg = "{\"fields\":"+JSON.toJSONString(cList)+",\"success\":true}";
            } else {
                msg = "{\"success\":false,\"msg\":\"Connection Refused\"}";
            }
        }
        return msg;
    }
}

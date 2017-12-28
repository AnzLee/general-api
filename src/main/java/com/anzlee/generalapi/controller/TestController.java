/**
 * 用户控制层
 *
 * @author li.liangzhe
 * @create 2017-11-23 14:01
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.entity.User;
import com.anzlee.generalapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/token/getToken")
    @ResponseBody
    public String index(@RequestBody test t){
        return "{\"token\":\"11111\"}";
    }

    @RequestMapping(value = "/content/addContent")
    @ResponseBody
    public boolean show(String sysName, String key, String subSectionId){
        return true;
    }
}
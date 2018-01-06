/**
 * 用户控制层
 *
 * @author li.liangzhe
 * @create 2017-11-23 14:01
 **/
package com.anzlee.generalapi.controller;

import com.anzlee.generalapi.coder.loader.FileClassLoader;
import com.anzlee.generalapi.entity.User;
import com.anzlee.generalapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/third/{className}")
    @ResponseBody
    public String handler(@PathVariable("className")String className){
        String wholeClassName = "com.anzlee.generalapi.third._"+className+"Controller";
        String methodName = "validate";
        FileClassLoader.loadClassAndInvokeMethod(wholeClassName, methodName, null);
        return wholeClassName;
    }
}
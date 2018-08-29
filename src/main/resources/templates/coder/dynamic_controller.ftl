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

/**
* 描述：${apiName}控制层
* @author GEN-API
* @date ${date}
*/
@Api(value="${apiName}",tags={"${apiName}"})
@Controller
@RequestMapping(value = "${apiName}")
public class _${apiName}Controller{

    /**
     * 描述：接口方法
     *
     <#list apiParams as apiParam>
     * @param ${apiParam.paramName}  ${apiParam.paramValue}
     </#list>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiImplicitParams({
    <#list apiParams as apiParam>
    @ApiImplicitParam(name="${apiParam.paramName}",value="${apiParam.paramValue}",dataType="${apiParam.paramType}", paramType = "query"),
    </#list>
    })
    public String CommonMethod(@RequestParam("param") String param)throws Exception{
        System.out.println("_${apiName}Controller is running");
        return param;
    }

    public void validate(){
        System.out.println("_${apiName}Controller has been Loaded");
    }
}

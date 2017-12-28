/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.coder.compiler.CompilerUtils;
import com.anzlee.generalapi.coder.generator.util.FreeMarkerTemplateUtils;
import com.anzlee.generalapi.dao.APIRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.service.CoderService;
import com.anzlee.generalapi.util.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CoderServiceImpl implements CoderService {
    private final static Log logger = LogFactory.getLog(CoderService.class);

    @Autowired
    APIRepositoty apiRepositoty;

    @Override
    public boolean genByAPIID(Long apiID) {
        API api = apiRepositoty.findOne(apiID);
        if(api==null){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("apiName", api.getApiName());
        data.put("apiParams", api.getApiParams());
        data.put("date", sdf.format(new Date()));
        return FreeMarkerTemplateUtils.createFile("dynamic_controller.ftl", data, SystemUtils.getClassesPath()+"api_java_src/_"+api.getApiName()+"Controller.java");
    }

    @Override
    public boolean compiler() {
        try {
            String filePath = SystemUtils.getClassesPath()+"api_java_src";
            String sourceDir = SystemUtils.getClassesPath()+"api_java_src";
            String jarPath = SystemUtils.getProjectLibPath();
            String targetDir = SystemUtils.getClassesPath();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            CompilerUtils dynamicCompilerUtil = new CompilerUtils();
            boolean compilerResult = dynamicCompilerUtil.compiler("UTF-8", dynamicCompilerUtil.getJarFiles(jarPath), filePath, sourceDir, targetDir, diagnostics);
            if (compilerResult) {
                logger.info("编译成功");
                return true;
            } else {
                logger.info("编译失败");
                for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                    logger.info(diagnostic.getMessage(null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

/**
 * 动态代码业务实现
 *
 * @author li.liangzhe
 * @create 2017-12-30 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.coder.compiler.CompilerUtils;
import com.anzlee.generalapi.coder.generator.util.FreeMarkerTemplateUtils;
import com.anzlee.generalapi.coder.loader.FileClassLoader;
import com.anzlee.generalapi.dao.APIRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.service.CoderService;
import com.anzlee.generalapi.util.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class CoderServiceImpl implements CoderService {
    private final static Log logger = LogFactory.getLog(CoderService.class);

    @Autowired
    APIRepositoty apiRepositoty;

    @Autowired
    ApplicationContext context;

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
                logger.info("compile success");
                return true;
            } else {
                logger.info("compile fail");
                for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                    logger.info(diagnostic.getMessage(null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void loadClass(){
        String className = "com.anzlee.generalapi.third._documentController";
        String methodName = "validate";
        FileClassLoader.loadClassAndInvokeMethod(className, methodName, null);
    }

    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    private static void registerBean(String beanName, BeanDefinition beanDefinition, ApplicationContext context) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}

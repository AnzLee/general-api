/**
 * FreeMarker模板工具
 *
 * @author li.liangzhe
 * @create 2017-12-22 5:25
 **/
package com.anzlee.generalapi.coder.generator.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class FreeMarkerTemplateUtils {

    private FreeMarkerTemplateUtils(){}
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_25);

    static{
        //这里比较重要，用来指定加载模板所在的路径
        CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(FreeMarkerTemplateUtils.class, "/templates/coder/"));
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @param ftl 模板文件名,相对上面的模版根目录templates路径,例如/module/view.ftl templates/module/view.ftl
     * @param data 填充数据
     * @param targetFile 要生成的静态文件的路径,相对设置中的根路径,例如 "jsp/user/1.html"
     * @return
     */
    public  static boolean createFile(String ftl, Map<String,Object> data, String targetFile) {
        Writer out = null;
        try {
            // 创建Template对象
            Configuration cfg = CONFIGURATION;
            Template template = cfg.getTemplate(ftl);
            template.setOutputEncoding("UTF-8");

            File tFile = new File(targetFile);
            if(!tFile.getParentFile().exists()){
                tFile.getParentFile().mkdirs();
            }
            if(!tFile.exists()){
                tFile.createNewFile();
            }
            // 生成静态页面
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
            template.process(data, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static void clearCache() {
        CONFIGURATION.clearTemplateCache();
    }
}

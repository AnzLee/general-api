/**
 * 系统工具
 *
 * @author li.liangzhe
 * @create 2017-12-25 17:34
 **/
package com.anzlee.generalapi.util;

public class SystemUtils {
    public static String getClassesPath(){
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    public static String getProjectLibPath() {
        return getClassesPath().replace("classes","lib");
    }
}

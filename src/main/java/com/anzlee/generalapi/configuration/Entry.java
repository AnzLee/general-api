/**
 * 入口
 *
 * @author li.liangzhe
 * @create 2017-11-23 14:08
 **/
package com.anzlee.generalapi.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项目启动入口，配置包根路径
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = "com.anzlee.generalapi")
public class Entry {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Entry.class, args);
    }
}
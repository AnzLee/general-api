/**
 * Cors跨域配置
 *
 * @author li.liangzhe
 * @create 2017-12-29 9:24
 **/
package com.anzlee.generalapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*")
                .allowedOrigins("*")
                .maxAge(3600);
    }
}


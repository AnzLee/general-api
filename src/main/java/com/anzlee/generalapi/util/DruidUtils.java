/**
 * Druid工具类
 *
 * @author li.liangzhe
 * @create 2017-12-05 16:08
 **/
package com.anzlee.generalapi.util;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DruidUtils {
    private static Map<String, DruidDataSource> relation = new HashMap<String, DruidDataSource>();

    /**
     * 可配置数据源
     * @param driver
     * @param url
     * @param user
     * @param password
     * @param initialSize
     * @return
     */
    private static DruidDataSource createDataSource(String driver,String url, String user, String password, int initialSize){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        dataSource.setPoolPreparedStatements(false);
        relation.put(url, dataSource);
        return dataSource;
    }

    /**
     * 默认配置数据源
     * @param driver
     * @param url
     * @param user
     * @param password
     * @return
     */
    private static DruidDataSource createDataSource(String driver,String url, String user, String password){
        return createDataSource(driver, url, user, password, 5);
    }

    public static void closeDataSource(String url){
        if(url!=null){
            DruidDataSource dataSource = relation.get(url);
            if(dataSource!=null){
                dataSource.close();
            }
        }
    }

    /**
     * 新建连接池并获得连接
     * @param driver
     * @param url
     * @param user
     * @param password
     * @param initialSize
     * @return
     */
    public static Connection getConnection(String driver,String url, String user, String password, int initialSize){
        if("unknow".equals(driver) || "unknow".equals(url)){
            return null;
        }
        DruidDataSource dataSource = DruidUtils.createDataSource(driver, url, user, password, initialSize);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 新建默认连接池并获得连接
     * @param driver
     * @param url
     * @param user
     * @param password
     * @return
     */
    public static Connection getConnection(String driver,String url, String user, String password){
        return getConnection(driver, url, user, password, 1);
    }

    /**
     * 从已有连接池中获得连接
     * @param url
     * @return
     */
    public static Connection getConnection(String url){
        DruidDataSource dataSource = relation.get(url);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

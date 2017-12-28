///**
// * Druid工具
// *
// * @author li.liangzhe
// * @create 2017-12-05 15:49
// **/
//package com.anzlee.generalapi.factory;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
//import java.sql.Connection;
//
//public class ConnectionFactory {
//    private static DruidDataSource dataSource = null;
//
//    static {
//        try {
//            String driver = "com.mysql.jdbc.Driver";
//            String url = "jdbc:mysql://127.0.0.1/cs_sports?useUnicode=true&characterEncoding=UTF-8";
//            String user = "root";
//            String password = "123456";
//
//            dataSource = new DruidDataSource();
//            dataSource.setDriverClassName(driver);
//            dataSource.setUrl(url);
//            dataSource.setUsername(user);
//            dataSource.setPassword(password);
//            dataSource.setInitialSize(5);
//            dataSource.setMinIdle(1);
//            dataSource.setMaxActive(10);
//
//            dataSource.setPoolPreparedStatements(false);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static synchronized Connection getConnection() {
//        Connection conn = null;
//        try {
//            conn = dataSource.getConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }
//}

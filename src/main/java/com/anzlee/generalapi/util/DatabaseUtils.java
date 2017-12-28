/**
 * 数据库工具
 *
 * @author li.liangzhe
 * @create 2017-12-05 16:55
 **/
package com.anzlee.generalapi.util;

import com.anzlee.generalapi.entity.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUtils {
    /**
     * 根据数据库类型获取驱动类
     * @param type
     * @return
     */
    public static String type2Driver(Database.Type type){
        switch (type){
            case ORACLE : return "oracle.jdbc.driver.OracleDriver";
            case SQLSERVER : return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
            case DB2 : return "com.ibm.db2.jdbc.app.DB2Driver";
            case SYBASE : return "com.sybase.jdbc.SybDriver";
            case MYSQL : return "com.mysql.jdbc.Driver";
            case POSTGRESQL : return "org.postgresql.Driver";
            case DM : return "dm.jdbc.driver.DmDriver";
            default: return "unknow";
        }
    }

    /**
     * 转换成各个数据库的地址
     * @param url
     * @param type
     * @param dataName
     * @return
     */
    public static String convertUrl(String url, Database.Type type, String dataName){
        switch (type){
            case ORACLE : return "jdbc:oracle:thin:@"+url+":"+dataName;
            case SQLSERVER : return "jdbc:microsoft:sqlserver://"+url+";DatabaseName="+dataName;
            case DB2 : return "jdbc:db2://"+url+"/"+dataName;
            case SYBASE : return "jdbc:sybase:Tds:"+url+"/"+dataName;
            case MYSQL : return "jdbc:mysql://"+url+"/"+dataName+"?useUnicode=true&characterEncoding=UTF-8";
            case POSTGRESQL : return "jdbc:postgresql://"+url+"/"+dataName;
            case DM : return "jdbc:dm://"+url+"/"+dataName;
            default: return "unknow";
        }
    }

    /**
     * 获取所有表名
     * @param database
     * @return
     */
    public static String getAllTableSQL(Database database){
        switch (database.getDataType()){
            case ORACLE : return "select table_name from user_tables";
            case SQLSERVER : return "select name table_name FROM SysObjects Where XType='U' ORDER BY Name";
            case DB2 : return "select name table_name from sysibm.systables where type='T' and creator='"+database.getDataUsername()+"'";
            case SYBASE : return "select name table_name from sysobjects where xtype='U'";
            case MYSQL : return "select table_name from information_schema.tables where table_schema='"+database.getDataName()+"'";
            case POSTGRESQL : return "select tablename table_name from pg_tables where schemaname='public'";
            case DM : return "select table_name from user_tables";
            default: return "unknow";
        }
    }

    public static void closeConn(Connection conn, PreparedStatement pstm, ResultSet rs){
        if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); }}
        if (pstm != null) { try { pstm.close(); } catch (Exception e) { e.printStackTrace(); }}
        if (conn != null) { try { conn.close(); } catch (Exception e) { e.printStackTrace(); }}
    }

    public static String getFieldsSQL(Database.Type type, String tableName) {
        switch (type){
            case ORACLE : return "select column_name FROM user_tab_columns WHERE table_name='"+tableName+"'";
            case SQLSERVER : return "select name column_name FROM SysColumns Where id=Object_Id('"+tableName+"')";
            case DB2 : return "select column_name from syscolumns where tbname='"+tableName+"'";
            case SYBASE : return "select column_name from information_schema.columns where table_name = '"+tableName+"'";
            case MYSQL : return "select column_name from information_schema.COLUMNS where table_name = '"+tableName+"'";
            case POSTGRESQL : return "select attname as column_name from pg_attribute where attrelid = ( select relfilenode from pg_class where relname = '"+tableName+"') and attnum > 0;";
            case DM : return "select column_name FROM user_tab_columns WHERE table_name='"+tableName+"'";
            default: return "unknow";
        }
    }
}

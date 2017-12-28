/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.entity.Database;
import com.anzlee.generalapi.service.DatabaseService;
import com.anzlee.generalapi.util.DatabaseUtils;
import com.anzlee.generalapi.util.DruidUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService{

    @Override
    public List<String> validDatabase(Database database) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String url = null;
        List<String> tList = new ArrayList<String>();
        try {
            String driver = DatabaseUtils.type2Driver(database.getDataType());
            url = DatabaseUtils.convertUrl(database.getDataPath(),database.getDataType(),database.getDataName());

            conn = DruidUtils.getConnection(driver,url,database.getDataUsername(),database.getDataPassword(),1);
            String sql = DatabaseUtils.getAllTableSQL(database);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                tList.add(rs.getString("table_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            DatabaseUtils.closeConn(conn, pstm, rs);
            DruidUtils.closeDataSource(url);
        } finally {
            DatabaseUtils.closeConn(conn, pstm, rs);
            return tList;
        }
    }

    @Override
    public List<String> getFields(String path, Database.Type type, String name, String tableName) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<String> cList = new ArrayList<String>();
        try{
            String url = DatabaseUtils.convertUrl(path, type, name);
            conn = DruidUtils.getConnection(url);
            String sql = DatabaseUtils.getFieldsSQL(type, tableName);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()){
                cList.add(rs.getString("column_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cList;
    }
}

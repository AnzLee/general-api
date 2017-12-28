/**
 * 用户服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.Database;

import java.util.List;

public interface DatabaseService {
    List<String> validDatabase(Database database);

    List<String> getFields(String path, Database.Type type, String name, String tableName);
}

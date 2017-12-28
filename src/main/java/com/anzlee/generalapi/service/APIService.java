/**
 * 用户服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.API;

public interface APIService {
    API findById(int id);
    API findByName(String name);
    API save(API api);
}

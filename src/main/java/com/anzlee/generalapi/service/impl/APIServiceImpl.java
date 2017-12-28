/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.dao.APIRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class APIServiceImpl implements APIService {

    @Autowired
    APIRepositoty apiRepositoty;

    @Override
    public API findById(int id) {
        return null;
    }

    @Override
    public API findByName(String name) {
        return null;
    }

    @Override
    public API save(API api) {
        return apiRepositoty.save(api);
    }
}

/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.dao.APIRepositoty;
import com.anzlee.generalapi.dao.DatabaseRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Database;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.service.APIService;
import com.anzlee.generalapi.service.TaskService;
import com.anzlee.generalapi.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class APIServiceImpl implements APIService {

    @Autowired
    APIRepositoty apiRepositoty;

    @Autowired
    DatabaseRepositoty databaseRepositoty;

    @Autowired
    TaskService taskService;

    @Override
    public API findById(Long id) {
        return apiRepositoty.findOne(id);
    }

    @Override
    public API findByName(String name) {
        return apiRepositoty.findByAPIName(name);
    }

    @Override
    @Transactional
    public API save(API api, Long task) {
        Task apiTask = taskService.findById(task);
        api.setApiTask(apiTask);
        for(Database database : api.getApiDatabase()){
            database.setDataPassword(
                    EncryptUtils.encode(database.getDataPassword()));
            database.setDatabaseApi(api);
        }
        databaseRepositoty.save(api.getApiDatabase());
        return apiRepositoty.save(api);
    }

    @Override
    public Page<API> apiView(Pageable pageable){
        return apiRepositoty.findAll(pageable);
    }

    @Override
    public String apiView(Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page-1,limit);
        Page<API> apiPage = apiRepositoty.findAll(pageable);
        JSONObject json = new JSONObject();
        json.fluentPut("code","0");
        json.fluentPut("count", apiPage.getTotalElements());
        List<API> tempAPIs = new ArrayList<API>();
        for(API api : apiPage.getContent()){
            api.setApiTask(null);
            tempAPIs.add(api);
        }
        json.fluentPut("data",tempAPIs);
        return json.toJSONString();
    }

    @Override
    public List<API> findAllApi() {
        return apiRepositoty.findAll();
    }
}

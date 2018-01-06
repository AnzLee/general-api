/**
 * 数据库服务实现
 *
 * @author li.liangzhe
 * @create 2017-12-05 14:41
 **/
package com.anzlee.generalapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anzlee.generalapi.dao.TaskRepositoty;
import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Task;
import com.anzlee.generalapi.service.APIService;
import com.anzlee.generalapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepositoty taskRepositoty;

    @Autowired
    APIService apiService;

    @Override
    public Task findById(Long id) {
        return taskRepositoty.findOne(id);
    }

    @Override
    public Task findByName(String name) {
        return taskRepositoty.findByTaskName(name);
    }

    @Override
    public Task save(Task task) {
        return taskRepositoty.save(task);
    }

    @Override
    @Transactional(value="transactionManager", rollbackFor = Exception.class)
    public Task saveWithAPI(Task task, Long[] apis) {
        List<API> taskApis = new ArrayList<API>();
        for(Long api:apis){
            API taskApi = apiService.findById(api);
            taskApis.add(taskApi);
        }
        task.setTaskApi(taskApis);
        return save(task);
    }

    @Override
    public Page<Task> taskView(Pageable pageable){
        return taskRepositoty.findAll(pageable);
    }

    @Override
    public String taskView(Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page-1,limit);
        Page<Task> taskPage = taskRepositoty.findAll(pageable);
        JSONObject json = new JSONObject();
        json.fluentPut("code","0");
        json.fluentPut("count", taskPage.getTotalElements());
        json.fluentPut("data",taskPage.getContent());
        return json.toJSONString();
    }

    @Override
    public List<Task> findAllTask() {
        return taskRepositoty.findAll();
    }
}

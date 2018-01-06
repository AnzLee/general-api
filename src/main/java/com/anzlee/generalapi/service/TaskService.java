/**
 * @author li.liangzhe
 * @create 2018-01-04 10:58
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task findByName(String name);

    Task save(Task task);

    Task saveWithAPI(Task task, Long[] apis);

    Task findById(Long id);

    Page<Task> taskView(Pageable pageable);

    String taskView(Integer page, Integer limit);

    List<Task> findAllTask();
}

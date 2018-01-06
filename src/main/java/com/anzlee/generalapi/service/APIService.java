/**
 * 用户服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface APIService {

    API findById(Long id);

    API findByName(String name);

    API save(API api, Long task);

    Page<API> apiView(Pageable pageable);

    String apiView(Integer page, Integer limit);

    List<API> findAllApi();

}

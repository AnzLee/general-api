/**
 * 用户服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.API;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface APIService {

    API findById(Long id);

    API findByName(String name);

    API save(API api);

    Page<API> apiView(Pageable pageable);

    String apiView(Integer page, Integer limit);
}

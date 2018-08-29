/**
 * 动态代码服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.API;
import com.anzlee.generalapi.entity.Database;

import java.util.List;
import java.util.Map;

public interface CoderService {

    boolean genByAPIID(Long apiID);

    boolean compiler();

    void loadClass(String apiName);
}

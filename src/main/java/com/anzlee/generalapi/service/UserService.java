/**
 * 用户服务层接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:57
 **/
package com.anzlee.generalapi.service;

import com.anzlee.generalapi.entity.User;

public interface UserService {
    User findUserByName(String name);
}

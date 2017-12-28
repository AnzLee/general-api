/**
 * 用户服务层实现
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:58
 **/
package com.anzlee.generalapi.service.impl;

import com.anzlee.generalapi.entity.User;
import com.anzlee.generalapi.dao.UserRepositoty;
import com.anzlee.generalapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepositoty userRepositoty;

    public User findUserByName(String name){
        User user = null;
        try{
            user = userRepositoty.findByUserName(name);
        }catch (Exception e){}
        return user;
    }
}
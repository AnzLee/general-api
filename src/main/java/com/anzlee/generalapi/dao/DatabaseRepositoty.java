/**
 * 用户持久层实现
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:52
 **/
package com.anzlee.generalapi.dao;

import com.anzlee.generalapi.entity.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepositoty extends JpaRepository<Database,Long>{

}

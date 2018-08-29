/**
 * @author li.liangzhe
 * @create 2018-01-04 11:03
 **/
package com.anzlee.generalapi.dao;

import com.anzlee.generalapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TaskRepositoty extends JpaRepository<Task,Long> {

    @Query("select t from Task t where t.taskName = :taskName")
    Task findByTaskName(@Param("taskName") String name);

    @Modifying
    @Query("update Task t set t.lastExTime = :lastExTime where t.ID = :taskId")
    Integer updateLastExTime(@Param("taskId") Long id,@Param("lastExTime") Date lastExTime);

    @Modifying
    @Query("update Task t set t.taskStatus = :status where t.ID = :taskId")
    Integer updateTaskStatus(@Param("taskId") Long id,@Param("status") Task.Status status);
}
